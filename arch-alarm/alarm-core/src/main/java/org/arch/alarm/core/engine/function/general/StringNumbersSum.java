package org.arch.alarm.core.engine.function.general;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.apache.commons.lang.math.NumberUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @description: 截取字符串中的数字求和
 * @Date : 2021/4/11 8:44 AM
 * @Author : lait.zhang@gmail.com
 */
public class StringNumbersSum extends AbstractFunction {

    /**
     * @param arg1 原始字符串
     * @param arg2 替换的新字符串
     * @param arg3 被替换的字符串
     * @param arg4 被替换的字符串
     * @return
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3, AviatorObject arg4) {
        Object strValue = arg1.getValue(env);
        if(strValue == null) {
            return new AviatorDecimal(BigDecimal.ZERO);
        }
        String str = strValue.toString();
        String newValue = arg2.getValue(env).toString();
        String oldValue1 = arg3.getValue(env).toString();
        String oldValue2 = arg4.getValue(env).toString();
        String[] params = str.replace(oldValue1, newValue).replace(oldValue2, newValue)
                .split(newValue);
        BigDecimal secureFee = Stream.of(params).filter(p -> NumberUtils.isNumber(p)).map(p -> new BigDecimal(p))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new AviatorDecimal(secureFee);
    }

    @Override
    public String getName() {
        return "string.numbers.sum";
    }
}
