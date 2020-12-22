package org.arch.payment.sdk;


import java.util.Map;

public interface PayParam {

    /**
     * @See PayParam#signature()
     * @param prikeyvalue
     */
    @Deprecated
    void setSign(String prikeyvalue);

    /**
     * @See PayParam#convert(PayRequest payRequest)
     * @param map
     * @param payRequest
     * @return
     */
    @Deprecated
    PayParam convert(Map<String, Object> map, PayRequest payRequest);


    /////////////////////////////////////////////////////////////////////////////////////////////




    /**
     * 转换参数
     * @param payRequest
     * @param payConfigurable
     * @return
     */
    PayParam convert(PayRequest payRequest, PayConfigurable payConfigurable);


    /**
     * 签名
     * @param payConfigurable#prikey
     * @return
     */
    PayParam signature(PayConfigurable payConfigurable);

    /**
     * 加密
     * @param payConfigurable#secretKey
     * @return
     */
    default PayParam encrypt(PayConfigurable payConfigurable){
        return this;
    }

    /**
     * 解密
     * @param encryptKey
     * @return
     */
    default PayParam decrypt(PayConfigurable encryptKey) {
        return this;
    }







}
