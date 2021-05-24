package org.arch.alarm.core.engine;

import com.alibaba.fastjson.JSONObject;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Options;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.core.engine.function.config.PullAndCompare;
import org.arch.alarm.core.engine.function.datetime.Tdif;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @description: Aviator引擎执行器
 * @Date : 2018/9/7 下午3:21
 * @Author : lait.zhang@gmail.com
 */

@Slf4j
public final class ExpressionExecutor {
    static {
        AviatorEvaluator.setOption(Options.ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL, true);
        AviatorEvaluator.setOption(Options.MATH_CONTEXT, MathContext.DECIMAL128);
        AviatorEvaluator.addFunction(new Tdif());
        AviatorEvaluator.addFunction(new PullAndCompare());
    }

    private ExpressionExecutor() {
    }

    /**
     * 执行结果
     *
     * @param context 上下文对象
     * @return
     */
    public static Object execute(ExpressionContext context) {
        Object result = AviatorEvaluator.execute(context.getExpression(), context.getEnv(), context.isCached());
        log.info("Aviator执行器context={},result={}", JSONObject.toJSON(context), result);
        return result;
    }

    /**
     * 执行结果，返回boolean类型
     *
     * @param context 上下文对象
     * @return
     */
    public static boolean executeBoolean(ExpressionContext context) {
        return (Boolean) execute(context);
    }

    /**
     * 执行结果，返回double类型
     *
     * @param context 上下文对象
     * @return
     */
    public static double executeDouble(ExpressionContext context) {
        return Double.valueOf(execute(context).toString());
    }

    /**
     * 执行结果，返回BigDecimal类型
     *
     * @param context 上下文对象
     * @return
     */
    public static BigDecimal executeBigDecimal(ExpressionContext context) {
        return new BigDecimal(execute(context).toString());
    }

    /**
     * 执行结果，返回BigDecimal类型
     *
     * @param context 上下文对象
     * @return
     */
    public static String executeString(ExpressionContext context) {
        return (String) execute(context);
    }
}
