package org.arch.payment.sdk;

import lombok.Data;

/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 4/9/2020
 * @Description ${Description}
 */
@Data
public class ChannelDirectiveConfiguration implements PayConfigurable {
    // 私钥
    private String privateKey;
    // 商户号
    private String merchantNo;
    // 商户码/用户名/商户名
    private String merchantCode;
    // 公钥
    private String publicKey;
    //密钥/密码
    private String secretKey;
    // uri 不一样
    private String directiveUri;
    // 回掉URL
    private String callbackUrl;
    // 跳转URL
    private String redirectUrl;

}
