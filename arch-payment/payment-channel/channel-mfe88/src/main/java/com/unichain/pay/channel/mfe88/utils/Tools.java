package com.unichain.pay.channel.mfe88.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sun.management.ManagementFactoryHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    public static final Gson gson = new Gson();
    //订单传递移位数
    private static final int LENGTH = 6;
    public static String[] BANK_NAME_KEY_ARRAY = {"邮政储蓄", "邮储银行", "工商银行", "农业银行", "中国银行", "建设银行", "中信银行", "光大银行",
            "华夏银行", "民生银行", "广发银行", "招商银行", "兴业银行", "浦发银行", "平安银行", "北京银行", "上海银行", "交通银行"};
    public static String[] BANK_CODE = {"PSBC", "PSBC", "ICBC", "ABC", "BOC", "CCB", "CITIC", "CEB", "HXBANK", "CMBC",
            "GDB", "CMB", "CIB", "SPDB", "SPABANK", "BJBANK", "SHBANK", "COMM"};
    private static Gson gsonDisableHtml;

    /**
     * 获取当前进程的pid
     *
     * @return
     */
    public static String getThirdPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        return pid;
    }

    public static Gson getGsonDisableHtml() {
        if (null == gsonDisableHtml) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonDisableHtml = gsonBuilder.disableHtmlEscaping().create();
        }
        return gsonDisableHtml;
    }

    public static String displacementEnString(String str) {
        return transpositionString(displacementEnString(str, LENGTH));
    }

    public static String displacementDeString(String str) {
        return displacementDeString(transpositionString(str), LENGTH);
    }

    /**
     * 订单号换位
     *
     * @param strtranspositionString
     * @return
     */
    public static String transpositionString(String str) {
        str = Tools.obj2str(str);
        if (str.length() < 1)
            return str;
        char[] strs = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        int charLength = strs.length / 2;
        for (int i = 0; i < charLength; i++) {
            buffer.append(strs[strs.length - i - 1]);
        }
        if (strs.length % 2 != 0) {
            buffer.append(strs[charLength]);
        }
        for (int i = charLength - 1; i >= 0; i--) {
            buffer.append(strs[i]);
        }
        return buffer.toString();
    }

    /**
     * 订单号移位加
     *
     * @param str
     * @param len
     * @return
     */
    public static String displacementEnString(String str, int len) {
        str = Tools.obj2str(str);
        if (str.length() < 1)
            return str;
        char[] strs = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        int charLength = strs.length;
        for (int i = 0; i < strs.length; i++) {
            int x = i;
            x = i + len;
            if (x > charLength - 1) {
                x = x - charLength;
            }
            buffer.append(strs[x]);
        }
        return buffer.toString();
    }

    /**
     * 订单号移位解
     *
     * @param str
     * @param len
     * @return
     */
    public static String displacementDeString(String str, int len) {
        str = Tools.obj2str(str);
        if (str.length() < 1)
            return str;
        char[] strs = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        int charLength = strs.length;
        for (int i = 0; i < strs.length; i++) {
            int x = i;
            x = i - len;
            if (x < 0) {
                x = x + charLength;
            }
            buffer.append(strs[x]);
        }
        return buffer.toString();
    }

    /**
     * 格式化Exception的堆栈信息,转换成String,便于记录日志
     *
     * @param e
     * @return
     * @Title: exceptionToString
     * @Description: TODO
     */
    public static String exceptionToString(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }

    /**
     * 判断字符数组是否为空
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * 格式化数字并保留两位小数
     *
     * @param num
     * @return
     * @Title: numFormat
     * @Description: TODO
     */
    public static String numFormat(String num) {
        DecimalFormat format = new DecimalFormat("#.##");
        Double numd = Double.valueOf(num);
        return format.format(numd);
    }

    /**
     * String to int
     */
    public static Integer string2Int(String str) {
        str = Tools.obj2str(str);
        return Tools.notEmpty(str) ? Integer.valueOf(str) : 0;
    }

    public static Integer string2Int(Object str) {
        String strs = Tools.obj2str(str);
        return Tools.notEmpty(strs) ? Integer.valueOf(strs) : 0;
    }

    public static String strToLongStr(String str, String n) {
        String rs = "0";
        if (null == str || null == n || str.length() == 0 || n.length() < 0) {
            return rs;
        }
        double strd = 0d;
        double nd = 0d;
        try {
            strd = Double.valueOf(str);
            nd = Double.valueOf(n);
        } catch (Exception e) {
            e.printStackTrace();
            return rs;
        }
        double rsd = strd * nd;
        String rsStr = String.valueOf(rsd);
        return rsStr;
    }

    /**
     * 将数组转换为带连接符链接的内容
     *
     * @param jion   拼接符号
     * @param strAry 处理数组
     * @return 设定文件
     * @throws @date 2016年6月2日上午9:30:38
     * @Title: stringJion
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @user pengxi
     */
    public static String stringJion(String jion, String[] strAry) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strAry.length; i++) {
            if (i == (strAry.length - 1)) {
                sb.append(strAry[i]);
            } else {
                sb.append(strAry[i]).append(jion);
            }
        }
        return sb.toString();
    }

    /**
     * 将数组转换为带连接符链接的内容
     *
     * @param jion   拼接符号
     * @param strAry 处理数组
     * @return 设定文件
     * @throws @date 2016年6月2日上午9:30:38
     * @Title: stringJion
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @user pengxi
     */
    public static String stringJion(String jion, Object[] strAry) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strAry.length; i++) {
            if (i == (strAry.length - 1)) {
                sb.append(strAry[i]);
            } else {
                sb.append(strAry[i]).append(jion);
            }
        }
        return sb.toString();
    }

    /**
     * obj转字符串
     *
     * @param s
     * @return
     */
    public static String obj2str(Object s) {
        if (s == null) {
            return "";
        } else if (Tools.isEmpty(s.toString())) {
            return "";
        } else {
            return s.toString();
        }
    }

    public static long obj2Long(Object s) {
        if (s == null) {
            return 0l;
        } else if (Tools.isEmpty(s.toString())) {
            return 0;
        } else {
            return new BigDecimal(s.toString().trim()).longValue();
        }
    }

    public static boolean isEmpty(Object s) {
        if (s == null) {
            return true;
        } else return Tools.isEmpty(s.toString());
    }

    /**
     * 随机生成六位数验证码
     *
     * @return
     */
    public static int getRandomNum() {
        Random r = new Random();
        return r.nextInt(900000) + 100000;// (Math.random()*(999999-100000)+100000)
    }

    /**
     * 检测字符串是否不为空(null,"","null")
     *
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s) {
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s);
    }

    /**
     * 字符串转换为字符串数组
     *
     * @param str        字符串
     * @param splitRegex 分隔符
     * @return 不包含末尾为空的字符
     */
    public static String[] str2StrArray(String str, String splitRegex) {
        return str2StrArrayTool(str, splitRegex, 0);
    }

    /**
     * 字符串转换为字符串数组
     *
     * @param str
     * @param splitRegex
     * @return 所有的截取字段，包含末尾空的字段
     */
    public static String[] str2StrArrayAll(String str, String splitRegex) {
        return str2StrArrayTool(str, splitRegex, -1);
    }

    /**
     * 字符截取工具
     *
     * @param str
     * @param splitRegex
     * @param length
     * @return
     */
    public static String[] str2StrArrayTool(String str, String splitRegex, int length) {
        if (isEmpty(str)) {
            return null;
        }
        if (length == 0) {
            return str.split(splitRegex);
        } else {
            return str.split(splitRegex, length);
        }
    }

    /**
     * 用默认的分隔符(,)将字符串转换为字符串数组
     *
     * @param str 字符串
     * @return
     */
    public static String[] str2StrArray(String str) {
        return str2StrArray(str, ",\\s*");
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String date2Str(Date date) {
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前时间
     *
     * @return String 返回类型
     * @Title: datenow
     * @Description: TODO
     * @throws:
     */
    public static String dateNow() {
        return date2Str(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
     *
     * @param date
     * @return
     */
    public static Date str2Date(String date) {
        if (notEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Date();
        } else {
            return null;
        }
    }

    /**
     * 时间格式化
     *
     * @param date
     * @param format
     * @return String 返回类型
     * @Title: str2formatDate 2016-12-20 19:37:33 ---> 其他格式
     * @Description: TODO
     * @throws:
     */
    public static String str2formatDate(String date, String format) {
        if (Tools.isEmpty(format)) {
            format = "yyyyMMdd";
        }
        if (notEmpty(date)) {
            Date dates = str2Date(date);
            return date2Str(dates, format);
        } else {
            return null;
        }
    }

    /**
     * 按照参数format的格式，日期转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 把时间根据时、分、秒转换为时间段
     *
     * @param StrDate
     */
    public static String getTimes(String StrDate) {
        String resultTimes = "";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now;

        try {
            now = new Date();
            Date date = df.parse(StrDate);
            long times = now.getTime() - date.getTime();
            long day = times / (24 * 60 * 60 * 1000);
            long hour = (times / (60 * 60 * 1000) - day * 24);
            long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            StringBuffer sb = new StringBuffer();
            // sb.append("发表于：");
            if (hour > 0) {
                sb.append(hour + "小时前");
            } else if (min > 0) {
                sb.append(min + "分钟前");
            } else {
                sb.append(sec + "秒前");
            }

            resultTimes = sb.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultTimes;
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile(
                    "^(((13[0-9])|(15([0-3]|[5-9]))|(17([0-3]|[5-9]))|(18([0-3]|5-9])))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 根据长度截取字符串
     *
     * @param str       被截取的字符串
     * @param cutLength 保留长度
     * @return 你好... ...
     */
    public static String strCutOut(String str, int cutLength) {
        return strCutOut(str, cutLength, "......");
    }

    /**
     * 根据长度截取字符串
     *
     * @param str       被截取的字符串
     * @param cutLength 保留长度
     * @param cutChar   截取后末尾添加符号；默认“...”
     * @return
     */
    public static String strCutOut(String str, int cutLength, String cutChar) {
        cutChar = Tools.isEmpty(cutChar) ? "..." : cutChar;
        if (Tools.isEmpty(str)) {
            return str;
        }
        int strLength = str.length();
        if (strLength > cutLength) {
            return str.substring(0, cutLength) + cutChar;
        }

        return str;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("signType")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilterN(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    public static String createUUID() {
        UUID id = UUID.randomUUID();
        return id.toString().replace("-", "");
    }// method

    public static String getBankCode(String name) {
        for (int i = 0; i < BANK_NAME_KEY_ARRAY.length; i++) {
            if (name.contains(BANK_NAME_KEY_ARRAY[i])) {
                return BANK_CODE[i];
            }
        }
        return "";
    }


    /**
     * 在字段前面添加内容
     *
     * @param str
     * @param len
     * @param joinStr
     * @return String 返回类型
     * @Title: stringJoin
     * @Description: TODO
     * @throws:
     */
    public static String stringJoin(String str, int len, String joinStr) {
        int strLength = Tools.obj2str(str).length();
        StringBuffer buffer = new StringBuffer();
        if (strLength < len) {
            len = len - strLength;
            for (int i = 0; i < len; i++) {
                buffer.append(joinStr);
            }
        }
        buffer.append(str);
        return buffer.toString();

    }

    /**
     * 将key =value &key =value 转换成map形式
     *
     * @param str
     * @return Map<String   ,   String> 返回类型
     * @Title: keyValue2Map
     * @Description: TODO
     * @throws:
     */
    public static Map<String, String> keyValue2Map(String str) {
        // checkcode=3000&realinfunds=200&message=成功
        Map<String, String> map = new HashMap<>();

        String keys = "";
        String values = "";
        String[] strs = str.split("&");
        for (String st : strs) {
            if (Tools.notEmpty(st)) {
                String[] keyvalue = st.split("=");
                if (keyvalue.length == 2) {
                    keys = keyvalue[0];
                    values = keyvalue[1];
                    if (Tools.notEmpty(keys)) {
                        map.put(Tools.obj2str(keys).trim(), Tools.obj2str(values).trim());
                    }
                }
            }

        }
        return map;

    }

    /**
     * 获取当前服务所在主机ip
     */
    public static String getIp() {
        String ips = "";
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        ips = ip.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            return "";
        }

        return ips;
    }

    /**
     * 获取PID号
     */
    public static String getPid() {
        String serviceName = ManagementFactoryHelper.getRuntimeMXBean().getName();
        // get pid
        String pid = serviceName.split("@")[0];
        return pid;
    }


}
