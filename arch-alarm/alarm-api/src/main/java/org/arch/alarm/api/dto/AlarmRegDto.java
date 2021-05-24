package org.arch.alarm.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/4/2021 12:00 AM
 */
@Data
public class AlarmRegDto implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 类目id
     */
    private Long catid;

    /**
     * 规则名称
     */
    private String regName;

    /**
     * 规则健，key唯一
     */
    private String regKey;

    /**
     * 规则默认值，参考值
     */
    private String regVal;

    /**
     * 规则参考值来源，可以是一个api接口，一条sql语句 如： http://xxx/getDeliveryTime…… sql://192.168.0.1@db@user:pwd/select * from tb……
     */
    private String regDatasource;

    /**
     * 规则对描述
     */
    private String descr;

    /**
     * 条件或条件表达式，如果没有按照coputer的逻辑计算，如果有computer按照该表达式计算
     */
    private String expression;

    /**
     * 对应ofs_alarm_computer表中单一个计算器
     */
    private Long computerId;

    /**
     * 计算器名称
     */
    private String computerName;

    /**
     * 参数计数器，用于分布式计算，收集参数计数
     */
    private Integer paramCount;

    /**
     * 规则模板通知内容中的占位符
     */
    private String placeholderJson;

}
