package com.unichain.pay.sharelink.utils;

import com.unichain.pay.sharelink.exception.PgwServiceException;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtils {

    /**
     * 日期格式 yyyy-MM-dd
     */
    public static final String DATE_FULL = "yyyy-MM-dd";
    /**
     * 日期格式 yyyy年MM月dd日
     */
    public static final String DATE_FULL_NAME = "yyyy年MM月dd日";
    /**
     * 日期格式 MM月dd日
     */
    public static final String DATE_MOTH_DAY = "MM月dd日";
    /**
     * 时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME_FULL = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式（无连接符）yyyyMM
     */
    public static final String DATE_SHORT_MONTH = "yyyyMM";
    /**
     * 日期格式 yyyy-MM
     */
    public static final String DATE_MONTH = "yyyy-MM";
    /**
     * 日期格式（无连接符）yyyyMMdd
     */
    public static final String DATE_SHORT = "yyyyMMdd";
    /**
     * 时间格式（无连接符） yyyyMMddHHmmss
     */
    public static final String DATETIME_SHORT = "yyyyMMddHHmmss";
    /**
     * 时间格式（无连接符） yyyyMMddHH
     */
    public static final String DATETIME_HOUR = "yyyyMMddHH";
    /**
     * 时间格式（无连接符）HHmmss
     */
    public static final String TIME_SHORT = "HHmmss";
    /**
     * 时间格式 HH:mm:ss
     */
    public static final String TIME_FULL = "HH:mm:ss";
    /**
     * 时间格式 yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String DATETIME_FULL_S = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 时间格式yyyyMMddHHmmssSSS 18位ID PCAC专用
     */
    public static final String DATETIME_FULL_SS = "yyyyMMddHHmmsssSSS";
    /**
     * 时间格式YYYYMMdd HH:mm:ss
     */
    public static final String DATETIME_FULL_QUICK_PAY = "yyyyMMdd HH:mm:ss";

    /**
     * 时间格式YYYY.MM.dd HH:mm:ss
     */
    public static final String DATETIME_FULL_WITHPOINT = "yyyyMMdd HH:mm:ss";

    /**
     * 时间格式yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME_ECT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_ECT_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 时间格式YYYY.MM
     */
    public static final String DATE_SHORT_MONTH_2 = "yyyy.MM";

    /**
     * 时间格式MMDDhhmmss
     */
    public static final String DATETIME_FULL_MMDDHHMMSS = "MMddHHmmss";

    /**
     * 时间格式hhmmss
     */
    public static final String DATETIME_FULL_HHMMSS = "hhmmss";

    /**
     * 时间格式MMDD
     */
    public static final String DATETIME_FULL_MMDD = "MMdd";

    /**
     * 时间格式YYMM
     */
    public static final String DATETIME_FULL_YYMM = "yyMM";

    /**
     * 时间格式DD
     */
    public static final String DATETIME_FULL_DD = "dd";

    /**
     * 时间格式D
     */
    public static final String DATETIME_FULL_D = "d";

    public static final String PERIOD_REALTIME = "00"; // 实时
    public static final String PERIOD_DAY = "01"; // 日
    public static final String PERIOD_WEEK = "02"; // 周
    public static final String PERIOD_HALFMONTH = "03"; // 半月
    public static final String PERIOD_MONTH = "04"; // 月
    public static final String PERIOD_SEASON = "05"; // 季
    public static final String PERIOD_HALFYEAR = "06"; // 半年
    public static final String PERIOD_YEAR = "07"; // 年

    public static final int ONE_DAY = 1;

    public static String toString(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    public static String toString(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public static String toString(String date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 根据参数格式显示格式化日期
     *
     * @param aDate java.lang.String
     * @param aFmt  java.lang.String
     * @return java.lang.String
     */
    public static String getFmtDate(String aDate, String aFmt) {
        Date dt = null;
        SimpleDateFormat inFmt = null, outFmt = null;
        java.text.ParsePosition pos = new java.text.ParsePosition(0);
        if ((aDate == null) || (aDate.trim().equals("")))
            return "";
        try {
            if (Long.parseLong(aDate) == 0)
                return "";
        } catch (Exception nume) {
            return aDate;
        }
        if ((aFmt == null) || (aFmt.trim().equals("")))
            aFmt = "yyyy-MM-dd ";
        aDate = aDate.trim();
        try {
            switch (aDate.length()) {
                case 14:
                    inFmt = new SimpleDateFormat("yyyyMMddHHmmss");
                    break;
                case 12:
                    inFmt = new SimpleDateFormat("yyyyMMddHHmm");
                    break;
                case 10:
                    inFmt = new SimpleDateFormat("yyyyMMddHH");
                    break;
                case 8:
                    inFmt = new SimpleDateFormat("yyyyMMdd");
                    break;
                case 6:
                    inFmt = new SimpleDateFormat("yyyyMM");
                    break;
                default:
                    return aDate;
            }
            if ((dt = inFmt.parse(aDate, pos)) == null)
                return aDate;
            outFmt = new SimpleDateFormat(aFmt);
            return outFmt.format(dt);
        } catch (Exception ex) {
            return aDate;
        }
    }

    public static Date toDate(String source, String pattern) throws PgwServiceException {
        try {
            return new SimpleDateFormat(pattern).parse(source);
        } catch (ParseException e) {
            throw new PgwServiceException(e.getMessage());
        }
    }

    /**
     * 判断某个日期是否是制定周期日
     *
     * @param date
     * @param periodType
     * @return
     */
    public static boolean isPeriodDay(Date date, String periodType) {
        SimpleDateFormat DATE_FORMATE =
                new SimpleDateFormat(DATE_SHORT);

        return isPeriodDay(DATE_FORMATE.format(date), periodType);
    }

    public static boolean isPeriodDay(String date, String periodType) {
        if (date == null)
            return false;
        if (periodType == null)
            return true;

        // 每年
        return PERIOD_REALTIME.equals(periodType) // 实时
                || PERIOD_DAY.equals(periodType) // 每天
                || (PERIOD_WEEK.equals(periodType)
                && isWeekDay(date)) // 每周
                || (PERIOD_HALFMONTH.equals(periodType)
                && isHalfMonthEnd(date)) // 每半月
                || (PERIOD_MONTH.equals(periodType)
                && isMonthEnd(date)) // 每月
                || (PERIOD_SEASON.equals(periodType)
                && isSeasonEnd(date)) // 每季
                || (PERIOD_HALFYEAR.equals(periodType)
                && isHalfYearEnd(date)) // 每半年
                || (PERIOD_YEAR.equals(periodType)
                && isYearEnd(date));
    }

    /**
     * 判断是否是周日
     *
     * @param date
     * @return
     */
    public static boolean isWeekDay(String date) {
        Calendar tmpCal = convertDateToCalendar(date);
        return tmpCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * 判断是否是周几
     *
     * @param date
     * @return
     */
    public static String getWeekDay(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    /**
     * 判断是否是半月底
     *
     * @param date
     * @return
     */
    public static boolean isHalfMonthEnd(String date) {
        Calendar tmpCal = convertDateToCalendar(date);
        return isMonthEnd(date) || tmpCal.get(Calendar.DAY_OF_MONTH) == 15;
    }

    /**
     * 判断是否是月底
     *
     * @param date
     * @return
     */
    public static boolean isMonthEnd(String date) {
        Calendar tmpCal = convertDateToCalendar(date);
        return tmpCal.get(Calendar.DAY_OF_MONTH)
                == tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断是否是季末
     *
     * @param date
     * @return
     */
    public static boolean isSeasonEnd(String date) {

        if (isMonthEnd(date)) {
            Calendar tmpCal = convertDateToCalendar(date);
            int month = tmpCal.get(Calendar.MONTH);
            return Calendar.MARCH == month
                    || Calendar.JUNE == month
                    || Calendar.SEPTEMBER == month
                    || Calendar.DECEMBER == month;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是半年末
     *
     * @param date
     * @return
     */
    public static boolean isHalfYearEnd(String date) {

        if (isMonthEnd(date)) {
            Calendar tmpCal = convertDateToCalendar(date);
            int month = tmpCal.get(Calendar.MONTH);
            return Calendar.JUNE == month || Calendar.DECEMBER == month;
        } else {
            return false;
        }
    }

    /**
     * 判断是否年末
     *
     * @param date
     * @return
     */
    public static boolean isYearEnd(String date) {

        if (isMonthEnd(date)) {
            Calendar tmpCal = convertDateToCalendar(date);
            int month = tmpCal.get(Calendar.MONTH);
            return Calendar.DECEMBER == month;
        } else {
            return false;
        }
    }

    /**
     * 转Calendar
     *
     * @param paramString
     * @return
     */
    public static Calendar convertDateToCalendar(String paramString) {
        int i = Integer.parseInt(paramString.substring(0, 4));
        int j = Integer.parseInt(paramString.substring(4, 6)) - 1;
        int k = Integer.parseInt(paramString.substring(6, 8));
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(i, j, k, 0, 0, 0);
        return calendar;
    }

    /**
     * Function : 获取下一天<br/>
     * 2015年6月11日 上午10:42:09 <br/>
     *
     * @param date
     * @return
     * @author caicj
     * @since JDK 1.7
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, ONE_DAY);
        return calendar.getTime();
    }

    /**
     * Function : 获取前一天<br/>
     * 2015年7月30日 下午8:43:21 <br/>
     *
     * @param date
     * @return
     * @author caicj
     * @since JDK 1.7
     */
    public static Date getLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -ONE_DAY);
        return calendar.getTime();
    }

    /**
     * Function : 将Date转化成 XMLGregorianCalendar类型<br/>
     * 2015年8月14日 上午11:56:01 <br/>
     *
     * @param date 日期
     * @return
     * @author JsonChen
     * @since JDK 1.7
     */
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
        XMLGregorianCalendar xmlGc = null;
        if (null == date) {
            return xmlGc;
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        try {
            xmlGc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException e) {
            return xmlGc;
        }
        return xmlGc;
    }

    /**
     * Function : 将XMLGregorianCalendar转换成 Date类型<br/>
     * 2015年8月14日 下午12:02:30 <br/>
     *
     * @param xmlGregorianCalendar
     * @return
     * @author JsonChen
     * @since JDK 1.7
     */
    public static Date convertToDate(XMLGregorianCalendar xmlGregorianCalendar) {
        GregorianCalendar gregorianCalendar = xmlGregorianCalendar.toGregorianCalendar();
        return gregorianCalendar.getTime();
    }

    /**
     * Function : 获取偏移日期<br/>
     * 2015年8月14日 下午8:43:21 <br/>
     *
     * @param date
     * @param delayDays 偏移日
     * @return
     * @author caicj
     * @since JDK 1.7
     */
    public static Date getAnotherDay(Date date, int delayDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, delayDays);
        return calendar.getTime();
    }

    /**
     * 根据限额类型及交易日期判断是否重置限额
     *
     * @param limitType 限额类型 01 单笔限额 02 日累计限额 03 月累计限额
     * @param tranDate  交易日期
     * @return true 需要清零， false 不需要清零
     */
    public static boolean isResetRec(String limitType, Date tranDate) {
        if (limitType.equals(R.PGW_AMT_LIMTT_TYPE.AMT_LIMTI_DAY)) {
            Date nowDate = null;
            try {
                nowDate = DateUtils.toDate(DateUtils.toString(DATE_SHORT), DATE_SHORT);
            } catch (PgwServiceException e) {
            }
            if (tranDate.compareTo(nowDate) < 0) {//日累计， != 不在同一天就要重置交易日期及交易额
                return true;
            }
        }
        if (limitType.equals(R.PGW_AMT_LIMTT_TYPE.AMT_LIMTI_MONTH)) {
            // 在本月之外
            //本月之外，需要重置月累计交易额及交易日期
            return tranDate.compareTo(DateUtils.getFirstDayOfMon()) < 0
                    || tranDate.compareTo(DateUtils.getLastDayOfMon()) > 0;
        }
        return false;
    }

    /**
     * 获取每月最后一天的日期
     */
    public static Date getLastDayOfMon() {
        Calendar maxCal = Calendar.getInstance();
        maxCal.set(Calendar.DATE, maxCal.getActualMaximum(Calendar.DATE));
        try {//先将日期转化成yyyyMMdd格式的字符串，除去时分秒，再转化成日期对象，参与比较
            return DateUtils.toDate(DateUtils.toString(maxCal.getTime(), DateUtils.DATE_SHORT), DateUtils.DATE_SHORT);
        } catch (PgwServiceException e) {
        }
        return null;
    }

    /**
     * 获取每月第一天的日期
     */
    public static Date getFirstDayOfMon() {
        Calendar maxCal = Calendar.getInstance();
        maxCal.set(Calendar.DATE, maxCal.getActualMinimum(Calendar.DATE));
        try {//先将日期转化成yyyyMMdd格式的字符串，除去时分秒，再转化成日期对象，参与比较
            return DateUtils.toDate(DateUtils.toString(maxCal.getTime(), DateUtils.DATE_SHORT), DateUtils.DATE_SHORT);
        } catch (PgwServiceException e) {
        }
        return null;
    }

    /**
     * 获取当年的第一天
     *
     * @param year
     * @return
     */
    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 得到当年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonthDate(int month) {
        Calendar currCal = Calendar.getInstance();
        int year = currCal.get(Calendar.YEAR);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * 得到当年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int month) {
        Calendar currCal = Calendar.getInstance();
        int year = currCal.get(Calendar.YEAR);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_SHORT);
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    /**
     * 得到前一年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonthOldYearDate(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * 得到前一年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonthOldYear(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_SHORT);
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    /**
     * 获取当年某月最后一天
     *
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int month) {
        Calendar currCal = Calendar.getInstance();
        int year = currCal.get(Calendar.YEAR);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_SHORT);
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 获取前一年某月最后一天
     *
     * @param month
     * @return
     */
    public static String getLastDayOfMonthOldYear(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_SHORT);
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 获取前一年某月最后一天
     *
     * @param month
     * @return
     */
    public static Date getLastDayOfMonthOldYearDate(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return cal.getTime();
    }

    /**
     * 获取当年某月最后一天
     *
     * @param month
     * @return
     */
    public static Date getLastDayOfMonthDate(int month) {
        Calendar currCal = Calendar.getInstance();
        int year = currCal.get(Calendar.YEAR);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return cal.getTime();
    }

    public static boolean equals(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        return date1.compareTo(date2) == 0;
    }

    /**
     * 获取自然日前一天日期
     * Function : getlastNatureDay<br/>
     * 2015年12月31日 上午11:37:50 <br/>
     *
     * @return
     * @throws PgwServiceException
     * @author caicj
     * @since JDK 1.7
     */
    public static Date getlastNatureDay() throws PgwServiceException {
        return DateUtils.toDate(DateUtils.toString(org.apache.commons.lang3.time.DateUtils.addDays(new Date(), -1), DateUtils.DATE_SHORT), DateUtils.DATE_SHORT);
    }

    /**
     * 获取指定日期所属月第一天
     */
    public static java.sql.Date getFirstDayOfMonth(java.sql.Date curDate) {
        Calendar day = Calendar.getInstance();
        day.setTime(curDate);
        day.set(Calendar.DAY_OF_MONTH, 1);
        return new java.sql.Date(day.getTime().getTime());
    }

    /**
     * 获取指定日期所属月的最后一天
     */
    public static java.sql.Date getLastDayOfMonth(java.sql.Date curDate) {
        Calendar day = Calendar.getInstance();
        day.setTime(curDate);
        day.add(Calendar.MONTH, 1);
        day.set(Calendar.DAY_OF_MONTH, 1);
        day.add(Calendar.DATE, -1);
        return new java.sql.Date(day.getTime().getTime());
    }

    /**
     * 获取指定日期所属季度的第一天
     */
    public static java.sql.Date getFirstDayOfSeason(java.sql.Date curDate) {
        Calendar day = Calendar.getInstance();
        day.setTime(curDate);
        int curMonth = day.get(Calendar.MONTH);
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
            day.set(Calendar.MONTH, Calendar.JANUARY);
        }
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
            day.set(Calendar.MONTH, Calendar.APRIL);
        }
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.SEPTEMBER) {
            day.set(Calendar.MONTH, Calendar.JULY);
        }
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
            day.set(Calendar.MONTH, Calendar.OCTOBER);
        }
        day.set(Calendar.DAY_OF_MONTH, day.getActualMinimum(Calendar.DAY_OF_MONTH));
        return new java.sql.Date(day.getTime().getTime());
    }

    /**
     * 获取自然日偏移日期
     * Function : getAnotherNatureDay<br/>
     * 2016年3月30日 上午11:37:50 <br/>
     *
     * @param delayDays 偏移日
     * @return
     * @throws PgwServiceException
     * @author caicj
     * @since JDK 1.7
     */
    public static Date getAnotherNatureDay(int delayDays) throws PgwServiceException {
        return DateUtils.toDate(DateUtils.toString(org.apache.commons.lang3.time.DateUtils.addDays(new Date(), delayDays), DateUtils.DATE_SHORT), DateUtils.DATE_SHORT);
    }

    /**
     * 获取日期所属的月份
     * Function : getCurrentMonth<br/>
     * 2016年5月3日 上午11:37:50 <br/>
     *
     * @param date 日期
     * @return
     * @throws Exception
     * @author pengxiao
     * @since JDK 1.7
     */
    public static int getCurrentMonth(Date date) throws Exception {
        SimpleDateFormat DATE_FORMATE = new SimpleDateFormat(DateUtils.DATE_SHORT);
        Calendar tmpCal = DateUtils.convertDateToCalendar(DATE_FORMATE.format(date));
        int month = tmpCal.get(Calendar.MONTH);
        return month;
    }

    /**
     * 获取日期所属的年份
     * Function : getCurrentMonth<br/>
     * 2016年5月3日 上午11:37:50 <br/>
     *
     * @param date 日期
     * @return
     * @throws Exception
     * @author pengxiao
     * @since JDK 1.7
     */
    public static int getCurrentYear(Date date) throws Exception {
        SimpleDateFormat DATE_FORMATE = new SimpleDateFormat(DateUtils.DATE_SHORT);
        Calendar tmpCal = DateUtils.convertDateToCalendar(DATE_FORMATE.format(date));
        int year = tmpCal.get(Calendar.YEAR);
        return year;
    }


    /**
     * 获取指定日期所属月第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.DAY_OF_MONTH, 1);
        return new Date(day.getTime().getTime());
    }

    /**
     * 获取指定日期所属月的最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.add(Calendar.MONTH, 1);
        day.set(Calendar.DAY_OF_MONTH, 1);
        day.add(Calendar.DATE, -1);
        return new Date(day.getTime().getTime());
    }

    /**
     * 获取指定日期所属月第一天
     */
    public static Date getAnotherMonth(Date date, int DelayMonths) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.add(Calendar.MONTH, DelayMonths);
        return new Date(day.getTime().getTime());
    }

    /**
     * 获取指定日期所属季度的第一天
     */
    public static Date getFirstDayOfSeason(Date curDate) {
        Calendar day = Calendar.getInstance();
        day.setTime(curDate);
        int curMonth = day.get(Calendar.MONTH);
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
            day.set(Calendar.MONTH, Calendar.JANUARY);
        }
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
            day.set(Calendar.MONTH, Calendar.APRIL);
        }
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.SEPTEMBER) {
            day.set(Calendar.MONTH, Calendar.JULY);
        }
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
            day.set(Calendar.MONTH, Calendar.OCTOBER);
        }
        day.set(Calendar.DAY_OF_MONTH, day.getActualMinimum(Calendar.DAY_OF_MONTH));
        return new Date(day.getTime().getTime());
    }


    /**
     * 获取指定日期所属半年的第一天
     */
    public static Date getFirstDayOfHalfYear(Date curDate) {
        Calendar day = Calendar.getInstance();
        day.setTime(curDate);
        int curMonth = day.get(Calendar.MONTH);
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.JUNE) {
            day.set(Calendar.MONTH, Calendar.JANUARY);
        }
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.DECEMBER) {
            day.set(Calendar.MONTH, Calendar.JULY);
        }
        day.set(Calendar.DAY_OF_MONTH, day.getActualMinimum(Calendar.DAY_OF_MONTH));
        return new Date(day.getTime().getTime());
    }

    /**
     * 获取指定日期所属年的第一天
     */
    public static Date getFirstDayOfYear(Date curDate) {
        Calendar day = Calendar.getInstance();
        day.setTime(curDate);
        day.set(Calendar.MONTH, Calendar.JANUARY);
        day.set(Calendar.DAY_OF_MONTH, day.getActualMinimum(Calendar.DAY_OF_MONTH));
        return new Date(day.getTime().getTime());
    }
}
