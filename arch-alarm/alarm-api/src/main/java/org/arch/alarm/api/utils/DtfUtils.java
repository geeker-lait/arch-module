package org.arch.alarm.api.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/10/2021 3:35 PM
 */
public class DtfUtils {

    /**
     * 获取时长
     *
     * @param datetime
     * @param hmsPattern
     * @return
     */
    public static Long getDurationByHmsPattern(String datetime, String hmsPattern) {
        if (null == datetime || datetime.isEmpty()) {
            throw new RuntimeException(" datetime format err, dateime must not null!");
        }
        // 默认格式化为分钟
        datetime = "now".equalsIgnoreCase(datetime) ? String.valueOf(LocalDateTime.now()) : datetime;
        // 默认格式化为分钟
        //hmsPattern = null == hmsPattern ? "m" : hmsPattern;
        if ("h".equalsIgnoreCase(hmsPattern)) {
            return LocalDateTime.parse(datetime).toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000 / 60 / 60;
        } else if ("m".equalsIgnoreCase(hmsPattern)) {
            return LocalDateTime.parse(datetime).toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000 / 60;
        } else if ("s".equalsIgnoreCase(hmsPattern)) {
            return LocalDateTime.parse(datetime).toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000;
        } else {
            return LocalDateTime.parse(datetime).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        }
    }
}
