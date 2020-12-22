package org.arch.payment.sdk;

public interface PayConfigurable {

    String getPrivateKey();

    void setPrivateKey(String privateKey);

    String getMerchantNo();

    String getMerchantCode();
    void setMerchantCode(String merchantCode);

    void setMerchantNo(String merchantId);

    String getPublicKey();

    void setPublicKey(String Key);

    String getSecretKey();

    void setSecretKey(String secretKey);

    String getDirectiveUri();

    void setDirectiveUri(String uri);

}
