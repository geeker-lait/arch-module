package org.arch.alarm.core.engine.function.object;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.Map;

/**
 * @description: 数字转字符串，如果数字为空，则使用默认值作为返回值，objects.toString(value,defaultValue)
 * @Date : 2019/10/11 下午6:48
 * @Author : lait.zhang@gmail.com
 */
public class ToString extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1,AviatorObject arg2) {
        Object originValue = arg1.getValue(env);
        Object defaultValue = arg2.getValue(env);
        boolean isNull = null == originValue || originValue.toString().length() == 0;
        return new AviatorString(isNull ? defaultValue.toString() : originValue.toString());
    }

    @Override
    public String getName() {
        return "objects.toString";
    }

}
