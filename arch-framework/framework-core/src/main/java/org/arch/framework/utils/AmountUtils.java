package org.arch.framework.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额元分之间转换工具类
 */
public class AmountUtils {

    public static DecimalFormat fnum = new DecimalFormat("##0.00000000000000000000");
    /**
     * 金额为分的格式
     */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

    /**
     * 将分为单位的转换为元并返回金额格式的字符串 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(Long amount) throws Exception {
        if (!amount.toString().matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }

        int flag = 0;
        String amString = amount.toString();
        if (amString.charAt(0) == '-') {
            flag = 1;
            amString = amString.substring(1);
        }
        StringBuffer result = new StringBuffer();
        if (amString.length() == 1) {
            result.append("0.0").append(amString);
        } else if (amString.length() == 2) {
            result.append("0.").append(amString);
        } else {
            String intString = amString.substring(0, amString.length() - 2);
            for (int i = 1; i <= intString.length(); i++) {
                if ((i - 1) % 3 == 0 && i != 1) {
                    result.append(",");
                }
                result.append(intString, intString.length() - i, intString.length() - i + 1);
            }
            result.reverse().append(".").append(amString.substring(amString.length() - 2));
        }
        if (flag == 1) {
            return "-" + result.toString();
        } else {
            return result.toString();
        }
    }

    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(String amount) throws Exception {
        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        return BigDecimal.valueOf(Long.parseLong(amount)).divide(new BigDecimal(100)).toString();
    }

    /**
     * 将元为单位的转换为分 （乘100）
     *
     * @param amount
     * @return
     */
    public static String changeY2F(BigDecimal amount) {
        return amount.multiply(new BigDecimal(100)).intValue() + "";
    }

    /**
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
     *
     * @param amount
     * @return
     */
    public static String changeY2F(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }

    /**
     * 格式化金额
     *
     * @param value
     * @return
     */
    public static String formatMoney(String value) {
        if (value == null || value == "") {
            value = "0.00";
        }
        return fnum.format(new BigDecimal(value));
    }

    public static String add(String valueStr, String addStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal augend = new BigDecimal(addStr);
        return fnum.format(value.add(augend));
    }

    public static Double add(Double valueStr, Double addStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal augend = new BigDecimal(addStr);
        return value.add(augend).doubleValue();
    }

    public static BigDecimal add(BigDecimal value, BigDecimal augend) {
        return value.add(augend);
    }

    public static String subtract(String valueStr, String subtrStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal subtrahend = new BigDecimal(subtrStr);
        return fnum.format(value.subtract(subtrahend));
    }

    public static Double subtract(Double valueStr, Double subtrStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal subtrahend = new BigDecimal(subtrStr);
        return value.subtract(subtrahend).doubleValue();
    }

    public static BigDecimal subtract(BigDecimal value, BigDecimal subtrahend) {
        return value.subtract(subtrahend);
    }

    public static String multiply(String valueStr, String mulStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(mulStr);
        return fnum.format(value.multiply(mulValue));
    }

    public static Double multiply(Double valueStr, Double mulStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(mulStr);
        return value.multiply(mulValue).doubleValue();
    }

    public static BigDecimal multiply(BigDecimal value, BigDecimal mulValue) {
        return value.multiply(mulValue);
    }

    public static String divide(String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal divideValue = new BigDecimal(divideStr);
        return fnum.format(value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP));
    }

    public static Double divide(Double valueStr, Double divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal divideValue = new BigDecimal(divideStr);
        return value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal divide(BigDecimal value, BigDecimal divideValue) {
        return value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP);
    }

    public static boolean compare(String valueStr, String compValueStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal compValue = new BigDecimal(compValueStr);
        // 0:等于 >0:大于 <0:小于
        int result = value.compareTo(compValue);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean compare(Double valueStr, Double compValueStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal compValue = new BigDecimal(compValueStr);
        // 0:等于 >0:大于 <0:小于
        int result = value.compareTo(compValue);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean compare(BigDecimal valueStr, BigDecimal compValueStr) {
        // 0:等于 >0:大于 <0:小于
        int result = valueStr.compareTo(compValueStr);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 金额乘以，省去小数点
     *
     * @param valueStr 基础值
     * @return
     */
    public static String moneyMulOfNotPoint(String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(divideStr);
        valueStr = fnum.format(value.multiply(mulValue));
        return fnum.format(value.multiply(mulValue)).substring(0, valueStr.length() - 3);
    }

    public static Integer getWeixinMoney(BigDecimal decimal) {
        if (decimal != null) {
            int value = decimal.multiply(BigDecimal.valueOf(100)).intValue();
            return value;
        }
        return null;
    }

}
