package org.arch.payment.sdk;

import org.arch.payment.sdk.order.impl.TransferOrder;

import java.util.Map;

/**
 * 转账类型
 */
public interface TransferType extends TransactionType {
    /**
     * 设置属性
     *
     * @param attr  已有属性对象
     * @param order 转账订单
     * @return 属性对象
     */
    Map<String, Object> setAttr(Map<String, Object> attr, TransferOrder order);
}
