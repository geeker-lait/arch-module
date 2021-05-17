package org.arch.alarm.core.engine.function.number;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @description: 多个数字求最大值(其中任何一个数字为空则作为0处理)
 * @Date : 2018/9/7 下午5:40
 * @Author : lait.zhang@gmail.com
 */
public class Max extends AbstractVariadicFunction {
    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
        List<BigDecimal> list = new ArrayList<>();
        for(AviatorObject each : args){
            Object value = each.getValue(env);
            if(value != null){
                list.add(new BigDecimal(value.toString()));
            }
        }
        list.sort(Comparator.comparing(BigDecimal::doubleValue).reversed());
        return new AviatorDecimal(new BigDecimal(list.get(0).toString()));
    }

    @Override
    public String getName() {
        return "max";
    }
}
