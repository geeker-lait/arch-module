package org.arch.alarm.core.engine.function.object;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * @description: 为空时，设置一个默认值
 * @Date : 2018/9/7 下午5:40
 * @Author : lait.zhang@gmail.com
 */
public class Nvl extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        Object originValue = arg1.getValue(env);
        Object defaultValue = arg2.getValue(env);
        return new AviatorDecimal(new BigDecimal(Optional.ofNullable(originValue).orElse(defaultValue).toString()));
    }

    @Override
    public String getName() {
        return "nvl";
    }
}
