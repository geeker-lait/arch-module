package org.arch.alarm.core.engine.function.number;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.yonghui.ofs.alarm.center.computer.engine.util.ArithUtil;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @description: 对数字取整方式 和 精度进位 处理
 * @Date : 2018/9/12 下午6:48
 * @Author : lait.zhang@gmail.com
 */
public class Scale extends AbstractFunction {
    /**
     *
     * @param env
     * @param arg1
     * @param arg2 2、1、0、-1、-2、-3、-4
     * XSDLW(2,"保留小数点后两位"),
     * XSDYW(1,"保留小数点后一位"),
     * GW(0,"精确到整数"),
     * SW(-1,"精确到十位"),
     * BW(-2,"精确到百位"),
     * QW(-3,"精确到千位"),
     * WW(-4,"精确到万位");
     * @param arg3 0：四舍五入；1：向上取整；2：向下取整；
     * @return
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        Object value = arg1.getValue(env);
        Object scaleObj = arg2.getValue(env);
        Object roundTypeObj = arg3.getValue(env);
        int newScale = Integer.valueOf(scaleObj.toString());
        int roundType = Integer.valueOf(roundTypeObj.toString());
        BigDecimal originValue = new BigDecimal(value.toString());
        return AviatorDecimal.valueOf(ArithUtil.truncate(originValue,newScale,roundType));
    }

    @Override
    public String getName() {
        return "scale";
    }
}
