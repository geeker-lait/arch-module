package org.arch.alarm.core.engine.rule;

import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/29/2021 5:55 PM
 */
@Data
public class AlarmRegRule {
    // 规则id主键
    private Long id;

    // 规则表达式
    private String expression;
    private String regKey;
    private String regName;



}
