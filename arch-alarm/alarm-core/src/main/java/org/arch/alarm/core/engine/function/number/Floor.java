package org.arch.alarm.core.engine.function.number;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * @description: 向下取整
 * @Date : 2018/9/7 下午5:40
 * @Author : lait.zhang@gmail.com
 */
public class Floor extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg) {
        Object value = arg.getValue(env);
        return AviatorDecimal.valueOf(Math.floor(new Double(value.toString())));
    }

    @Override
    public String getName() {
        return "floor";
    }
}
