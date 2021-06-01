package org.arch.payment.sdk;

/**
 * 支付订单信息
 */
public interface Order extends Attrs {


    /**
     * 添加订单信息
     *
     * @param key   key
     * @param value 值
     */
    void addAttr(String key, Object value);

}
