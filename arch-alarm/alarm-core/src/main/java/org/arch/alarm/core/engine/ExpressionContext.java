package org.arch.alarm.core.engine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @description: Aviator 上下文
 * @Date : 2018/9/7 下午3:17
 * @Author : lait.zhang@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpressionContext implements Serializable {
    /**
     * 表达式
     */
    private String expression;
    /**
     * 表达式参数
     */
    private Map<String, Object> env;
    /**
     * 是否缓存
     */
    private boolean cached;
}
