package org.arch.alarm.core.engine.rule;

import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/29/2021 4:50 PM
 */
public interface RuleChain {

    /***
     * 匹配规则表达式chain
     * @param context
     * @param regRules：表达式执行结果
     */
    void matchRule(ExpressionContext context, List<AlarmRegRule> regRules);
}
