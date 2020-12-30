package org.arch.payment.sdk.request;


import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @description: 确认绑卡请求
 * @weixin PN15855012581
 * @date 12/30/2020 7:32 PM
 */
@Data
public class BindCardRequest {

    // 协议好
    private String protocolId;
    // 短信验证码
    private String smsCode;

}
