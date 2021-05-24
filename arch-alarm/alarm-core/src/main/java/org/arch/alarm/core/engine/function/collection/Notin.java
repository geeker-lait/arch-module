package org.arch.alarm.core.engine.function.collection;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;

/**
 * @description: 判断一个字符串是否在一个字符串区间集合中
 * @Date : 2018/9/7 下午5:40
 * @Author : lait.zhang@gmail.com
 */
public class Notin extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        Object target = arg1.getValue(env);
        //对于数字类型，保留整数部分
        String formatValue = new DecimalFormat("#").format(new Double(target.toString()));
        Object split = arg2.getValue(env);
        Object collection = arg3.getValue(env);
        boolean in = Arrays.asList(collection.toString().split(split.toString())).contains(formatValue);
        return AviatorBoolean.valueOf(!in);
    }

    @Override
    public String getName() {
        return "notin";
    }
}
