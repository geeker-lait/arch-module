package org.arch.payment.core.api;

import org.arch.payment.core.bean.TransactionType;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/29/2020 7:30 PM
 */
public interface PayType {


    /**
     * 根据支付类型获取交易类型
     *
     * @param transactionType 类型值
     * @return 交易类型
     */
    TransactionType getTransactionType(String transactionType);

    PayService getPayService(PayAccount apyAccount);
}
