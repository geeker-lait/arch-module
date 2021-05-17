package org.arch.alarm.biz.computer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.alarm.api.AlarmComputerService;
import org.arch.alarm.api.AlarmNoticeService;
import org.arch.alarm.api.dto.*;
import org.arch.alarm.api.enums.MsgNotifier;
import org.arch.alarm.api.pojo.AlarmMsgData;
import org.arch.alarm.api.pojo.biz.AlarmRegData;
import org.arch.alarm.api.pojo.ComputResult;
import org.arch.alarm.biz.AlarmDataService;
import org.arch.alarm.biz.CanalRowComputable;
import org.arch.alarm.biz.MsgNoticeable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/29/2021 1:14 AM
 */
@Slf4j
public abstract class AbstractCanalRowComputer<T extends AlarmRegData> implements CanalRowComputable<T>, ApplicationContextAware {
    @Autowired
    protected AlarmComputerService regComputerService;
    @Autowired
    protected AlarmNoticeService alarmNoticeService;
    @Autowired
    protected AlarmDataService alarmDataService;
    protected static final Map<MsgNotifier, MsgNoticeable> NOTIFIER_MAP = new HashMap<>();


    @Override
    public void comput(AlarmRegDto alarmRegDto, List<AlarmParamsDto> alarmParamsDtos, List<T> alarmRegDatas) {
        // todo 远程计算
        ComputResult computerResult = regComputerService.compute(alarmParamsDtos, alarmRegDatas);
        log.info("{} comput result : {}", this.getClass().getSimpleName(), JSONObject.toJSONString(computerResult));
        // todo 根据计算结果决定是否继续作业
        if (!computerResult.isSuccess() || !Boolean.TRUE.equals(computerResult.getResult())) {
            log.info("");
            return;
        }
        List<AlarmNoticeDto> alarmNoticeDtos = alarmNoticeService.getAlarmNotices(computerResult.getRegId());

        if (alarmNoticeDtos == null || alarmNoticeDtos.size() == 0) {
            log.info("");
            return;
        }
        // todo 填充消息数据
        AlarmMsgData alarmMsgData = alarmDataService.fillAlarmNoticeData(alarmRegDto, alarmParamsDtos, alarmRegDatas);

        // todo 调用模板发送消息
        alarmNoticeDtos.forEach(and -> {
            Long templateId = and.getTemplateId();
            Long channelId = and.getChannelId();

            AlarmTemplateDto alarmTemplateDto = alarmDataService.getTemplateById(templateId);
            AlarmChannelDto alarmChannelDto = alarmDataService.getChannelById(channelId);
            if (StringUtils.isBlank(alarmChannelDto.getChannelCode())) {
                log.warn("通知渠道配置错误，缺少 channel code 参数");
                return;
            }
            MsgNotifier msgNotifier = MsgNotifier.valueOf(alarmChannelDto.getChannelCode().toUpperCase());
            if (null != msgNotifier) {
                NOTIFIER_MAP.get(msgNotifier).notice(alarmTemplateDto, alarmChannelDto, and,alarmMsgData);
            }
        });
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (NOTIFIER_MAP.size() == 0) {
            applicationContext.getBeansOfType(MsgNoticeable.class).forEach((k, v) -> {
                NOTIFIER_MAP.put(v.getNotifier(), v);
            });
        }
    }

    public static void main(String[] args) {
        MsgNotifier msgNotifier = MsgNotifier.valueOf("email".toUpperCase());
        System.out.println(msgNotifier);
    }
}
