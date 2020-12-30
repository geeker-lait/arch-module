package org.arch.payment.sdk.request;

import lombok.Data;
import org.arch.payment.sdk.PayHeader;

import java.math.BigDecimal;

/**
 * @author lait.zhang@gmail.com
 * @description: 支付验证码请求
 * @weixin PN15855012581
 * @date 12/30/2020 9:13 PM
 */
@Data
public class PrePayingRequest {

    // 支付请求头
    private PayHeader payRequestHeader;

    // 金额
    private String amount;
}
