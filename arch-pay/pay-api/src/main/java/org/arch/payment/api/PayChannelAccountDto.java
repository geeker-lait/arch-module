package org.arch.payment.api;

import lombok.Data;
import org.arch.payment.api.enums.PayType;

@Data
public class PayChannelAccountDto {
    // 系统商户号(与parrnerNo一对多的关系)
    private String merchantNo;

    // 支付通道账号id
    private Long payChannelId;
    // 支付合作号|商户号(支付平台的账号或ID)
    private String partnerNo;

    // 应用id
    private String appId;
    // 支付平台公钥(签名校验使用)，sign_type只有单一key时public_key与private_key相等，比如sign_type=MD5的情况
    private String publicKey;
    // 应用私钥(生成签名)
    private String privateKey;
    // 应用密钥
    private String secretKey;
    // 异步回调地址
    private String notifyUrl;
    // 同步回调地址
    private String returnUrl;
    // 收款账号
    private String seller;

    //请求证书地址，请使用绝对路径
    private String keystorePath;
    //证书对应的密码
    private String storePassword;


    // 签名类型
    private String signType;
    // 编码类型 枚举值，字符编码 utf-8,gbk等等
    private String inputCharset;

    //支付类型,aliPay：支付宝，wxPay：微信, third: 三方,此处开发者自定义对应PayType枚举值
    private PayType payType;
    //是否为测试环境
    private boolean isTest = true;
}
