package org.arch.alarm.core.engine.function.object;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @description: 字符串转数字，如果字符串为空，则使用默认值作为返回值，objects.toNumber(value,defaultValue)
 * @Date : 2019/10/11 下午6:48
 * @Author : lait.zhang@gmail.com
 */
public class ToNumber extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1,AviatorObject arg2) {
        Object originValue = arg1.getValue(env);
        Object defaultValue = arg2.getValue(env);
        boolean isNull = null == originValue || originValue.toString().length() == 0;
        return AviatorDecimal.valueOf(isNull ? new BigDecimal(defaultValue.toString()) : new BigDecimal(originValue.toString()));
    }

    @Override
    public String getName() {
        return "objects.toNumber";
    }

}
