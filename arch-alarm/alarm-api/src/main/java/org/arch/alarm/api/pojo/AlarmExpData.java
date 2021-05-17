package org.arch.alarm.api.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lait.zhang@gmail.com
 * @description: 预警表达式数据
 * @weixin PN15855012581
 * @date 5/11/2021 5:38 PM
 */
@Data
public class AlarmExpData implements Serializable {
    // 规则key
    private String regKey;
    // 表达式参数名 expression param name
    private String epn;
    // 表达式参数值 expression param value
    private String epv;
    // 原参数名
    private String name;
    // java参数名
    private String javaName;
    // 原值
    private String value;
    // 数据类型
    private String mysqlType;
    // 是否忽略表达式计算
    private Boolean ignore = true;
}
