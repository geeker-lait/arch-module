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
public class ComputExpressDto implements Serializable {
    /**
     * 主键
     */
    private Long id;

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
     * 规则模板通知内容中的占位符
     */
    private String placeholderJson;



}
