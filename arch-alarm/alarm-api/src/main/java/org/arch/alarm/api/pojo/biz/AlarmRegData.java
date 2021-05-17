package org.arch.alarm.api.pojo.biz;

import org.arch.alarm.api.annotation.RegPropertyUtils;
import org.arch.alarm.api.pojo.AlarmExpData;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: 预警规则数据接口定义
 * @weixin PN15855012581
 * @date 4/28/2021 4:23 PM
 */
public interface AlarmRegData extends Serializable {

    AlarmRegData setStoreNo(String shopId);

    String getStoreNo();

    AlarmRegData setOrderId(String orderId);

    String getOrderId();

    AlarmRegData setOperatorId(String operatorId);

    String getOperatorId();

    AlarmRegData setOperatorName(String operatorName);

    String getOperatorName();

    AlarmRegData setOperatorMobile(String operatorMobile);

    String getOperatorMobile();

    AlarmRegData setDataState(String dataState);

    String getDataState();

    AlarmRegData setVersion(Long version);

    Long getVersion();

    // 原始数据+加工后的表达式数据
    AlarmRegData setAlarmExpDatas(List<AlarmExpData> alarmRowDatas);

    List<AlarmExpData> getAlarmExpDatas();

//    AlarmRegData setName(String name);
//    String getName();
//    AlarmRegData setValue(String name);
//    String getValue();
//    AlarmRegData setJavaName(String name);
//    String getJavaName();
//    AlarmRegData setMysqlType(String name);
//    String getMysqlType();

    /**
     * 获取表达式key:val
     *
     * @return
     */
    default Map<String, Object> toMap() {
        return RegPropertyUtils.toMap(this);
    }
}
