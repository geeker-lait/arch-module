package com.unichain.pay.channel.mfe88.demo.entity;


public class FontColorContent {
    /***
     * 获取颜色字
     * @param str
     * @param ColorEnum
     * @return
     */
    public static String getColorStr(String str, ColorEnum color) {
        StringBuffer strb = new StringBuffer();
        strb.append("</br><font color=\"");
        strb.append(color.getValue());
        strb.append("\">");
        strb.append(str);
        strb.append("</font>");
        return strb.toString();
    }


    public static String getFailStr() {
        return getColorStr("异常结束：", ColorEnum.ERROR);
    }

    public static String getSuccessStr() {
        return getColorStr("成功结束：", ColorEnum.ERROR);
    }

    public static String getUnknownStr() {
        return getColorStr("未知结果：", ColorEnum.INFO);
    }

}
