package org.arch.payment.sdk;

/**
 * 银行
 */
public interface Bank {

    /**
     * 获取银行的代码
     * @return 银行的代码
     */
    String getCode();

    /**
     * 获取银行的名称
     * @return 银行的名称
     */
    String getName();
}
