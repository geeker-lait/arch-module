package org.arch.payment.sdk.request;


import lombok.Data;
import org.arch.payment.sdk.PayFourElements;
import org.arch.payment.sdk.PayHeader;

/**
 * @author lait.zhang@gmail.com
 * @description: 确认绑卡请求
 * @weixin PN15855012581
 * @date 12/30/2020 7:32 PM
 */
@Data
public class BindCardRequest {
    // 支付请求头
    private PayHeader payRequestHeader;
    // 协议号
    private String protocolId;
    // 短信验证码或其他类型到验证标示
    private String verifyCode;

}
