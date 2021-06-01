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
//
//    // 私钥
//    private String privateKey;
//    // 商户号
//    private String merchantNo;
//    // 商户码/用户名/商户名
//    private String merchantCode;
//    // 公钥
//    private String publicKey;
//    //密钥/密码
//    private String secretKey;
//    // uri 不一样
//    private String directiveUri;
//    // 回掉URL
//    private String callbackUrl;
//    // 跳转URL
//    private String redirectUrl;

}
