package org.arch.alarm.core.engine.function.datetime;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: 时间差值计算
 * @weixin PN15855012581
 * @date 5/10/2021 11:29 AM
 */
public class Tdif extends AbstractFunction {
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
    //一天的毫秒数
    private final static long ND = 1000 * 24 * 60 * 60;
    //一小时的毫秒数
    private final static long NH = 1000 * 60 * 60;
    //一分钟的毫秒数
    private final static long NM = 1000 * 60;
    //一秒的毫秒数
    private final static long NS = 1000;

    /**
     * @param env
     * @param arg1
     * @param arg2
     * @return
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        Object datetime1 = arg1.getValue(env);
        Object datetime2 = arg2.getValue(env);
        String formatValue = FunctionUtils.getStringValue(arg3, env);
        if (null == formatValue || formatValue.trim().length() != 1) {
            throw new RuntimeException("func format param err!");
        }
        if (!"dhms".contains(formatValue)) {
            throw new RuntimeException(" datetime format err! this func need a args at least, for example ：TDIF('now' ,'2021:05:10 12:13:22','d/h/m/s') or TDIF('2021:05:10 12:13:22','now','d/h/m/s')");
        }
        long now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        if ("now".equalsIgnoreCase(datetime1.toString())) {
            datetime1 = now;
        }
        if ("now".equalsIgnoreCase(datetime2.toString())) {
            datetime2 = now;
        }
        long between = Math.abs(Long.valueOf(datetime1.toString()) - Long.valueOf(datetime2.toString()));
        long day = between / ND;
        long hour = between % ND / NH;
        long min = between % ND % NH / NM;
        long sec = (between / NS);
        if ("d".equalsIgnoreCase(formatValue)) {
            // 天
            return AviatorLong.valueOf(day);
        } else if ("h".equalsIgnoreCase(formatValue)) {
            // 时
            return AviatorLong.valueOf(day * 24 + hour);
        } else if ("m".equalsIgnoreCase(formatValue)) {
            // 分
            return AviatorLong.valueOf((day * 24 + hour) * 60 + min);
        } else if ("s".equalsIgnoreCase(formatValue)) {
            // 秒
            return AviatorLong.valueOf(sec);
        }
        return null;
    }

    @Override
    public String getName() {
        return "TDIF";
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.parse(LocalDateTime.now().toString()).toEpochSecond(ZoneOffset.of("+8")) / 1000 / 60);
    }
}
