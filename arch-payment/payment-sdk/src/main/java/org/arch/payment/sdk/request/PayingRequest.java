package org.arch.payment.sdk.request;

import lombok.Data;
import org.arch.payment.sdk.PayHeader;

/**
 * @author lait.zhang@gmail.com
 * @description: 确认支付请求
 * @weixin PN15855012581
 * @date 12/30/2020 9:08 PM
 */
@Data
public class PayingRequest {
    // 支付请求头
    private PayHeader payHeader;
    // 金额
    private String amount;
    // 短信或其他类型到验证标示
    private String verifyCode;
    // 协议号 第三方支付可能去要,由第三方在发起支付验证后返回
    private String protocolId;

}
