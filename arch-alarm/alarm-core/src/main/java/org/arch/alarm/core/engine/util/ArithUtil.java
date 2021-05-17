package org.arch.alarm.core.engine.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @description: 数字计算精度、进位方式的处理
 * @Date : 下午3:51 2018/4/15
 * @Author : lait.zhang@gmail.com
 */
public final class ArithUtil {
    // 默认除法运算精度
    private static final int DEF_DIV_SCALE=10;

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v  需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 如果是整数位的进位，则调用此方法。
     * @param value
     * @param roundType
     * @param scale
     * @return
     */
    public  static BigDecimal zhengshuRound(BigDecimal value , int roundType , int scale){
        //解释：我们要将423.5 精确到百位，先将423.5/100变成4.235， 然后将4.235进行取整后为4，最后将4*100=400， 则为最后结果。
        if(scale<0){
            Double temp = Math.pow(10,Math.abs(scale));
            BigDecimal jinWei = new BigDecimal(temp.intValue());
            value = value.divide(jinWei, 16, BigDecimal.ROUND_HALF_UP).setScale(0,getRoundType(roundType)).multiply(jinWei);
            return value;
        }else {
            throw new RuntimeException("参数 scale 不符合要求！");
        }
    }

    /**
     * 获取取证方式
     * @param roundType
     * @return
     */
    public static int getRoundType(int roundType){
        switch (roundType){
            case 0:
                return BigDecimal.ROUND_HALF_UP;
            case 1:
                return BigDecimal.ROUND_UP;
            case 2:
                return BigDecimal.ROUND_DOWN;
            default:
                return BigDecimal.ROUND_HALF_UP;
        }
    }

    /**
     * 对value精度和取整方式处理
     * @param value 原值
     * @param scale 取整方式
     * @param roundType 精度枚举
     */
    public static BigDecimal truncate(BigDecimal value, int scale, int roundType) {
        if(scale >= 0) {
            value = value.setScale(scale, getRoundType(roundType));
        }else{
            value = zhengshuRound(value,roundType,scale);
        }
        return value;
    }

    /**
     * @param value
     * @return
     * @Description: Object类型数据转BigDecimal
     */
    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(value.toString());
            } else {
                throw new RuntimeException("Not possible to coerce "+value+" from class "+value.getClass()+" into a BigDecimal" );
            }
        }
        return  ret;
    }
}
