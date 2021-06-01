package org.arch.payment.sdk;

public interface BillType {
    /**
     * 获取类型名称
     * @return 类型
     */
    String getType();

    /**
     * 获取类型对应的日期格式化表达式
     * @return 日期格式化表达式
     */
    String getDatePattern();

    /**
     * 获取文件类型
     * @return 文件类型
     */
    String getFileType();

    /**
     * 自定义属性
     * @return 自定义属性
     */
    String getCustom();
}