package org.arch.alarm.api.pojo.biz;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.arch.alarm.api.pojo.AlarmExpData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/6/2021 8:31 PM
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class AlarmCommonRegData implements AlarmRegData {
    // 订单id
    private String orderId;
    // 仓店id
    private String storeNo;

    // 操作人员信息
    private String operatorId;
    private String operatorName;
    private String operatorMobile;
    // 数据状态
    private String dataState;
    // 数据版本
    private Long version;

    // 原始数据
    private List<AlarmExpData> alarmExpDatas = new ArrayList<>();
//    private String regKey;
//    private String name;
//    private String javaName;
//    private String value;
//    private String mysqlType;
}
