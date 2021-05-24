package org.arch.alarm.biz.notifier;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.arch.alarm.api.dto.AlarmChannelDto;
import org.arch.alarm.api.dto.AlarmNoticeDto;
import org.arch.alarm.api.dto.AlarmTemplateDto;
import org.arch.alarm.api.enums.MsgNotifier;
import org.arch.alarm.api.enums.UserTyp;
import org.arch.alarm.api.pojo.AlarmMsgData;
import org.arch.alarm.api.pojo.AlarmRequest;
import org.arch.alarm.biz.MsgNoticeable;
import org.arch.alarm.biz.notifier.weixin.WxProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/8/2021 6:25 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WeixinNotifier extends AbstractMsgNotifier implements MsgNoticeable {
    private final RestTemplate restTemplate;
    private final ThreadPoolTaskExecutor workWechatGroupAlertExecutor;

    private static final String ALERT_GROUP_NAME = "履约关键信息告警组";
    private static final String ALERT_TYPE = "groupchat";
    private static final String MESSAGE_TYPE = "markdown";


    @Override
    public MsgNotifier getNotifier() {
        return MsgNotifier.WEIXIN;
    }

    @Override
    public void notice(AlarmTemplateDto alarmTemplateDto, AlarmChannelDto alarmChannelDto, AlarmNoticeDto alarmNoticeDto, AlarmMsgData alarmMsgData) {
        log.info("微信通知.....");

        Integer userTyp = alarmNoticeDto.getUserTyp();
        List<String> ewxnums = new ArrayList<>();
        // 用户
        if (userTyp == UserTyp.USER.getTyp()) {
            // 企业微信
            //ewxnums.add(getUserCommunication(alarmNoticeDto.getStoreNo(), alarmNoticeDto.getUserId()).getEwxno());
        }
        //用户组
        if (userTyp == UserTyp.GROUP.getTyp()) {
            // 企业微信集合
            //List<String> nums = getUserGroupCommunication(alarmChannelDto.getStoreNo(), alarmNoticeDto.getUserId()).stream().map(UserCommunication::getEwxno).filter(StringUtils::isNotBlank).collect(Collectors.toList());
            //ewxnums.addAll(nums);
        }

        /**
         * todo 填充模板信息 规则名称
         */
        String conetent = alarmTemplateDto.getContent();
        // ums 根据仓店号 和 用户id 获取用户通信信息
        //UserCommunication userCommunication = getUserCommunication(alarmNoticeDto.getStoreNo(), alarmNoticeDto.getUserId());
        StrSubstitutor strSubstitutor = new StrSubstitutor(alarmMsgData.toMap());
        String nc = strSubstitutor.replace(conetent);
        sendNotice(alarmChannelDto, nc, "");
    }


    public void sendNotice(AlarmChannelDto alarmChannelDto, String message, String remark) {
        workWechatGroupAlertExecutor.submit(() -> {
            try {
                doSend(alarmChannelDto, message, remark);
            } catch (Exception e) {
                log.error("发送企业微信告警群异常，参数：{}", message, e);
            }
        });
    }

    /**
     * 发送报警信息到企业微信
     *
     * @param alarmChannelDto 通道对象
     * @param message         报警信息
     * @return
     */
    private void doSend(AlarmChannelDto alarmChannelDto, String message, String remark) {
        AlarmRequest request = new AlarmRequest();
        WxProperties wp = JSONObject.parseObject(alarmChannelDto.getExtJsonp(), WxProperties.class);
        if (wp != null) {
            request.setType(wp.getType());
            request.setMsgtype(wp.getMsgtype());
            request.setName(wp.getName());
            request.setRemark(remark);
            request.setMessage(message);
            request.setSign(alarmChannelDto.getSign());
            String requestJson = JSON.toJSONString(request);
            log.info("发送企业微信告警群请求参数：{}", requestJson);
            ResponseEntity<String> response = restTemplate.postForEntity(alarmChannelDto.getChannelUrl(), requestJson, String.class);
            log.info("发送企业微信告警群响应结果：{}", JSON.toJSONString(response.getBody()));
        } else {
            log.error("未能正确配置微信通道配置，请配置微信扩展属性字段ext_jsonp：{}的值", JSONObject.toJSONString(new WxProperties()));
        }
    }

    public static void main(String[] args) {
        WxProperties wp = new WxProperties();
        wp.setMsgtype("markdown");
        wp.setName("YH预警-TEST");
        wp.setSign("5dbae47db46d2de3ac30a4282b03b492");
        wp.setType("groupchat");
        System.out.println(JSONObject.toJSONString(wp));
    }
}
