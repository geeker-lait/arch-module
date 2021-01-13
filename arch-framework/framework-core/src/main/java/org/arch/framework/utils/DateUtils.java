package org.arch.framework.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @date 2020/1/1
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String YYYY = "yyyy";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String HH_MM_SS = "HH:mm:ss";

    public static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String DATETIME_CHINA = "yyyy年MM月dd日 HH:mm:ss";

    private static final String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 统计（日）的数组
     */
    public static final String[] DAY = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    /**
     * 统计（月） 的数组
     */
    public static final String[] MONTH = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获得当天是周几
     */
    public static String getWeekDay() {
//        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }


    public static SimpleDateFormat getDateFmt() {
        return new SimpleDateFormat(YYYY_MM_DD);
    }

    public static SimpleDateFormat getTimeFmt() {
        return new SimpleDateFormat(HH_MM_SS);
    }

    public static SimpleDateFormat getDateTimeFmt() {
        return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    }

    public static SimpleDateFormat getDateTimeCHFmt() {
        return new SimpleDateFormat(DATETIME_CHINA);
    }

    public static SimpleDateFormat getTimestampFmt() {
        return new SimpleDateFormat(TIMESTAMP);
    }

    public static Date newDateByDay(int day) {
        return newDateByDay(newDate(), day);
    }

    public static Date newDateByDay(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public static Date newDateBySecond(int second) {
        return newDateBySecond(newDate(), second);
    }

    public static Date newDateBySecond(Date d, int second) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.SECOND, now.get(Calendar.SECOND) + second);
        return now.getTime();
    }

    public static Date newDate() {
        return new Date();
    }

    public static Long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static Long currentTimeSecond() {
        return System.currentTimeMillis() / 1000;
    }

    public static Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String currentTimestampStr() {
        return getTimestampFmt().format(currentTimestamp());
    }

    public static String currentDateTimeCHStr() {
        return getDateTimeCHFmt().format(currentTimestamp());
    }

    public static String currentDateTimeStr() {
        return getDateTimeFmt().format(currentTimestamp());
    }

    public static String currentDateStr() {
        return getDateFmt().format(currentTimestamp());
    }

    public static String currentTimeStr() {
        return getTimeFmt().format(currentTimestamp());
    }

    public static String formatTimestamp(Date date) {
        return (date != null) ? getTimestampFmt().format(date) : null;
    }

    public static String formatDateTime(Date date) {
        return (date != null) ? getDateTimeFmt().format(date) : null;
    }

    public static String formatCHDateTime(Date date) {
        return (date != null) ? getDateTimeCHFmt().format(date) : null;
    }

    public static String formatDate(Date date) {
        return (date != null) ? getDateFmt().format(date) : null;
    }

    public static String formatTime(Date date) {
        return (date != null) ? getTimeFmt().format(date) : null;
    }

    public static Date parseTime(String date) {
        return parseTime(date, null);
    }

    public static Date parseTime(String date, Date defaultDate) {
        try {
            return (date != null) ? getTimeFmt().parse(date) : defaultDate;
        } catch (Exception e) {
            return defaultDate;
        }
    }

    public static Date parseDate(String date) {
        return parseDate(date, (Date) null);
    }

    public static Date parseDate(String date, Date defaultDate) {
        try {
            return (date != null) ? getDateFmt().parse(date) : defaultDate;
        } catch (Exception e) {
            return defaultDate;
        }
    }

    public static Date parseDateTime(String date) {
        return parseDateTime(date, null);
    }

    public static Date parseDateTime(String date, Date defaultDate) {
        try {
            return (date != null) ? getDateTimeFmt().parse(date) : defaultDate;
        } catch (Exception e) {
            return defaultDate;
        }
    }

    public static Timestamp parseTimestamp(String date) {
        return parseTimestamp(date, null);
    }

    public static Timestamp parseTimestamp(String date, Timestamp defaultDate) {
        try {
            return (date != null) ? Timestamp.valueOf(date) : defaultDate;
        } catch (Exception e) {
            return defaultDate;
        }
    }

    public static String hourToTime(int hour) {
        return String.format("%02d:00:00", hour);
    }

    public static long getTimeMillisConsume(long begintime) {
        return System.currentTimeMillis() - begintime;
    }

    public static long getTimeMillisConsume(Date begintime) {
        return getTimeMillisConsume(begintime.getTime());
    }

    public static long getTimeSecondConsume(Date begintime) {
        return getTimeMillisConsume(begintime) / 1000;
    }

    public static long getTimeMinuteConsume(Date begintime) {
        return getTimeSecondConsume(begintime) / 60;
    }

    public static Date getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static int getYear(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        return now.get(Calendar.YEAR);
    }

    public static Date getYesterdayOutWeek() {
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != 2) {
            return newDateByDay(getCurrentDay(), -1);
        } else {
            return newDateByDay(getCurrentDay(), -3);
        }
    }

    public static boolean betweenTimes(String compareTime, String beginTimeStr, String endTimeStr) {
        try {
            Date nowTime = DateUtils.parseTime(compareTime);
            Date beginTime = null;
            Date endTime = null;
            if (ObjectUtils.isNotEmpty(beginTimeStr)) {
                beginTime = DateUtils.parseTime(beginTimeStr);
            }
            if (ObjectUtils.isNotEmpty(endTimeStr)) {
                endTime = DateUtils.parseTime(endTimeStr);
                // 结束时间和开始时间做个比较（19.00.00 —— 2.00.00，结束时间需要加上1天 ）
                if (beginTime != null && endTime != null && endTime.before(beginTime)) {
                    endTime = DateUtils.newDateByDay(endTime, 1);
                }
            }

            if (beginTime != null && nowTime.before(beginTime)) {
                return false;
            }
            if (endTime != null && nowTime.after(endTime)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

