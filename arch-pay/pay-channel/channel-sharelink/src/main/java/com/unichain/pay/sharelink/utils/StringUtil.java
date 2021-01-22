package com.unichain.pay.sharelink.utils;

import java.math.BigDecimal;

public class StringUtil {

    /**
     * 去除前后空格
     *
     * @param str
     * @return
     */
    public static String trimString(String str) {
        return str.trim();
    }


    /**
     * 将字符串类型的Object转化为String类型,并去除前后空格
     *
     * @param obj
     * @return
     */
    public static String getStringFromObject(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString().trim();
    }

    /**
     * 判断字符串是否为空字符串,如果为空格则返回true,不为空则返回false
     *
     * @param args
     */
    public static boolean isBlank(String str) {
        if (str == null)
            return true;

        return str.trim().equals("");

    }

    /**
     * 将字符串转化成Bigdecima
     *
     * @param args
     */
    public static BigDecimal getBigDecimalFromString(String str) {
        if (trimString(str).equals("")) {
            return new BigDecimal("0.00");
        }
        return new BigDecimal(str);
    }

    public static String getPayAccountType(String payAccountType) {
        String payAccountypeTemp = "00";
        if (payAccountType.equals(R.PGWBindAcctTypeEnum.ACCT_TYPE_BORROW.getValue())
                || payAccountType.equals(R.PGWBindAcctTypeEnum.ACCT_TYPE_CREDIT.getValue())) {
            //银行卡或者信用卡
            payAccountypeTemp = "01";
        } else if (payAccountType.equals(R.PGWBindAcctTypeEnum.ACCT_TYPE_PREPAY.getValue())
                || payAccountType.equals(R.PGWBindAcctTypeEnum.ACCT_TYPE_OTHER.getValue())
                || payAccountType.equals(R.PGWBindAcctTypeEnum.ACCT_TYPE_TRADE.getValue())
                || payAccountType.equals(R.PGWBindAcctTypeEnum.ACCT_TYPE_CACCOUNT.getValue())
                || payAccountType.equals(R.PGWBindAcctTypeEnum.ACCT_TYPE_DERIVE.getValue())
                || payAccountType.equals(R.PGWBindAcctTypeEnum.ACCT_TYPE_FINANCE.getValue())
                || payAccountType.equals(R.PGWBindAcctTypeEnum.ACCT_TYPE_FUND.getValue())
                || payAccountType.equals(R.PGWBindAcctTypeEnum.ACCT_TYPE_FUTURES.getValue())) {
            //证券账户
            payAccountypeTemp = "02";
        }
        return payAccountypeTemp;
    }


}
