package org.arch.alarm.biz.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.arch.alarm.api.AlarmChannelService;
import org.arch.alarm.api.AlarmDicService;
import org.arch.alarm.api.AlarmParamsService;
import org.arch.alarm.api.dto.AlarmChannelDto;
import org.arch.alarm.api.dto.AlarmDicDto;
import org.arch.alarm.biz.config.properties.ChannelProperties;
import org.arch.alarm.biz.config.properties.DatabaseProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lait.zhang@gmail.com
 * @description: 该对象可以在初始化时自动从数据库中加载
 * @weixin PN15855012581
 * @date 4/29/2021 12:02 AM
 */
@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "spring.alarm")
public class AlarmConfig implements InitializingBean {

    private static final Map<String, List<AlarmChannelDto>> CHANNEL_MAP = new HashMap<>();
    private String ofsShopCenterDb;
    private String ofsPickCenterDb;
    private String ofsDeliveryCenterDb;
    private Long ofsOrderRedisKeyExpiredTime;
    @Autowired
    private AlarmDicService alarmDicService;
    @Autowired
    private AlarmChannelService alarmChannelService;
    @Autowired
    private AlarmParamsService alarmParamsService;

    @Value("${initOsfAlarmCenterDb:false}")
    private Boolean initOsfAlarmCenterDb;

    /**
     * 预警的通道
     */
    private List<ChannelProperties> channels;
    /**
     * 获取要监听的binlog
     * 注入application-binlog.yml配置中指定要监控Binlog的库表，注：该部分可配置在数据库中
     */
    private List<DatabaseProperties> databases;

    /**
     * 如果没有通过配置文件获取到该对象的属性值，则从数据库中自动加载，优先以本地配置文件为主
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (initOsfAlarmCenterDb) {
            if (null == databases) {
                return;
            }
            databases.forEach(db -> {
                db.getTables().forEach(tb -> {
                    // 过滤忽略的参数字典
                    tb.getColumns().stream().filter(c -> !c.getIgnore()).forEach(col -> {
                        AlarmDicDto alarmDicDto = new AlarmDicDto();
                        alarmDicDto.setSourceDatabase(db.getName());
                        alarmDicDto.setSourceTable(tb.getName());
                        alarmDicDto.setFiledCode(col.getName());
                        alarmDicDto.setFiledTyp(col.getType());
                        alarmDicDto.setFiledName(col.getDescr());
                        alarmDicService.configDic(alarmDicDto);
                    });
                });
            });

            if (null == channels) {
                return;
            }
            channels.forEach(c -> {
                AlarmChannelDto channelDto = new AlarmChannelDto();
                channelDto.setAppKey(c.getAppkey());
                channelDto.setChannelName(c.getName());
                channelDto.setChannelCode(c.getCode());
                channelDto.setSecrtKey(c.getSeckey());
                channelDto.setChannelVal(c.getVal());
                channelDto.setStoreNo(c.getStoreNo());
                channelDto.setChannelUrl(c.getUrl());
                channelDto.setCallbackUrl(c.getCallback());
                channelDto.setSign(c.getSign());
                alarmChannelService.configChannel(channelDto);
            });

            // 初始化参数


        }


        List<AlarmChannelDto> alarmChannelDtos = alarmChannelService.listChannel();
        if (null != alarmChannelDtos) {
            // 按照仓店id分组，目前只有一个，共用
            CHANNEL_MAP.putAll(alarmChannelDtos.stream().filter(acd -> acd.getStoreNo() != null).collect(Collectors.groupingBy(AlarmChannelDto::getStoreNo)));
        }

    }

    /**
     * 根据仓店号 和通道码获取通道配置信息
     *
     * @param storeNo
     * @param channelCode
     * @return
     */
    public AlarmChannelDto getChannelByStoreNoAndChannelCode(String storeNo, String channelCode) {
        if (storeNo == null || channelCode == null) {
            return null;
        }
        //CHANNEL_MAP.entrySet().stream().filter(k->k.getKey().equalsIgnoreCase(storeNo)).findAny();
        AlarmChannelDto alarmChannelDto = new AlarmChannelDto();
        CHANNEL_MAP.forEach((k, v) -> {
            if (k.equalsIgnoreCase(storeNo)) {
                Optional<AlarmChannelDto> dd = v.stream().filter(v1 -> v1.getChannelCode().equalsIgnoreCase(channelCode)).findFirst();
                if (dd.isPresent()) {
                    BeanUtils.copyProperties(dd.get(), alarmChannelDto);
                }
            }
        });
        return alarmChannelDto;
    }

//
//    @Bean
//    @ConditionalOnBean(name = {"picklistOrderDataConverter"})
//    public PicklistOrderDataConverter getPicklistOrderDataConverter() {
//        return new PicklistOrderDataConverter();
//    }
//
//
//    @Bean
//    @ConditionalOnBean(name = {"deliveryOrderDataConverter"})
//    public DeliveryOrderDataConverter getDeliveryOrderDataConverter() {
//        return new DeliveryOrderDataConverter();
//    }
}
