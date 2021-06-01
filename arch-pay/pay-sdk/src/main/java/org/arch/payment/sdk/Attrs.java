package org.arch.payment.sdk;

import java.io.Serializable;
import java.util.Map;

/**
 * 属性信息
 */
public interface Attrs extends Serializable {

    /**
     * 获取属性 这里可用做覆盖已设置的信息属性，订单信息在签名前进行覆盖。
     *
     * @return 属性
     */
    Map<String, Object> getAttrs();

    /**
     * 获取属性 这里可用做覆盖已设置的订单信息属性，订单信息在签名前进行覆盖。
     *
     * @param key 属性名
     * @return 属性
     */
    Object getAttr(String key);
}
