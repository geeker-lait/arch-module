package org.arch.alarm.biz;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.alarm.api.AlarmCatService;
import org.arch.alarm.api.AlarmChannelService;
import org.arch.alarm.api.AlarmTemplateService;
import org.arch.alarm.api.dto.*;
import org.arch.alarm.api.pojo.AlarmMsgData;
import org.arch.alarm.api.pojo.biz.AlarmRegData;
import org.arch.alarm.biz.cacher.AlarmRedisCacher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/11/2021 4:11 PM
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AlarmDataService {
    private final AlarmTemplateService alarmTemplateService;
    private final AlarmChannelService alarmChannelService;
    private final AlarmCatService alarmCatService;
    //private final ShopService shopService;
    private final AlarmRedisCacher alarmRedisCacher;
    /**
     * 来自ums领域,获取用户的个人信息（手机号，邮箱，微信等....）
     */
    //private final FUserCommunicationService FUmsService;

    @Value("${middleware.redis.alarmRedisKeyExpiredTime:360000}")
    protected Long alarmRedisKeyExpiredTime;

    //@Cacheable(value = "ofs-alarm-template", key = "#templateId")
    public AlarmTemplateDto getTemplateById(Long templateId) {
        Object obj = alarmRedisCacher.get("template-id-" + templateId);
        if (obj == null) {
            AlarmTemplateDto alarmTemplateDto = alarmTemplateService.getTemplateById(templateId);
            if (null != alarmTemplateDto) {
                alarmRedisCacher.update(templateId + "", alarmTemplateDto, alarmRedisKeyExpiredTime);
            }
            return alarmTemplateDto;
        }
        return (AlarmTemplateDto) obj;
    }

    public AlarmChannelDto getChannelById(Long channelId) {
        Object obj = alarmRedisCacher.get("channel-id-" + channelId);
        if (obj == null) {
            AlarmChannelDto alarmChannelDto = alarmChannelService.getChannelById(channelId);
            if (null != alarmChannelDto) {
                alarmRedisCacher.update(channelId + "", alarmChannelDto, alarmRedisKeyExpiredTime);
            }
            return alarmChannelDto;
        }
        return (AlarmChannelDto) obj;
    }

    public AlarmCatDto getCatById(Long catId) {
        Object obj = alarmRedisCacher.get("cat-id-" + catId);
        if (obj == null) {
            AlarmCatDto alarmCatDto = alarmCatService.getAlarmCatById(catId);
            if (null != alarmCatDto) {
                alarmRedisCacher.update(catId + "", alarmCatDto, alarmRedisKeyExpiredTime);
            }
            return alarmCatDto;
        }
        return (AlarmCatDto) obj;
    }

    public JSONObject getAlarmOrderId(String redisKey) {
        JSONObject jo = alarmRedisCacher.get(redisKey);
        return jo;
    }

    public void updateAlarmOrderId(String redisKey, JSONObject jsonObject, long expiredTime) {
        alarmRedisCacher.update(redisKey, jsonObject, expiredTime);
    }

    public AlarmMsgData fillAlarmNoticeData(AlarmRegDto alarmRegDto, List<AlarmParamsDto> alarmParamsDtos, List<? extends AlarmRegData> alarmRegDatas) {
        AlarmMsgData alarmMsgData = new AlarmMsgData();
        // 设置规则名称
        alarmMsgData.setRegName(alarmRegDto.getRegName());
        // 设置规则描述
        alarmMsgData.setDescr(alarmRegDto.getDescr());
        AlarmCatDto alarmCatDto = getCatById(alarmRegDto.getCatid());
        // 填充预警分类
        if (StringUtils.isNotBlank(alarmCatDto.getAlarmName())) {
            alarmMsgData.setAlarmCatName(alarmCatDto.getAlarmName());
        }
        AlarmRegData alarmRegData = alarmRegDatas.stream().filter(ard -> ard.getDataState().equals("after") && ard.getStoreNo() != null).findFirst().orElse(null);
        if (null == alarmRegData) {
            return alarmMsgData;
        }
        alarmMsgData.setOrderId(alarmRegData.getOrderId());

        alarmMsgData.setOperatorId(alarmRegData.getOperatorId());
        alarmMsgData.setOperatorName(alarmRegData.getOperatorName());
        alarmMsgData.setOperatorMobile(alarmMsgData.getOperatorMobile());
//        Shop shop = shopService.getShopById(alarmRegData.getStoreNo());
//        if (shop == null) {
//            return alarmMsgData;
//        }
//        // 设置业态
//        alarmMsgData.setBizName(getSellerName(shop.getSellerId()));
//        alarmMsgData.setProvince(shop.getProvince());
//        alarmMsgData.setCity(shop.getCity());
//        alarmMsgData.setStoreNo(shop.getShopName() + "[" + alarmRegData.getStoreNo() + "]");
        return alarmMsgData;
    }


    /**
     * 获取业态名称
     *
     * @param sellerId
     * @return
     */
    public static String getSellerName(Long sellerId) {
        // 业态
//        YHCommercial seller = YHCommercial.getYHCommercial(sellerId);
//        String sellerName = Objects.isNull(seller) ? String.valueOf(sellerId) : seller.getDescription();
//        return sellerName;
        return null;
    }


}
