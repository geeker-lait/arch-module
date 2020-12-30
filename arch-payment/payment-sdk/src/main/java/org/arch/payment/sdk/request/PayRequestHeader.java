package org.arch.payment.sdk.request;

import lombok.Data;
import lombok.NonNull;
import org.arch.payment.sdk.PayType;

/**
 * @author lait.zhang@gmail.com
 * @description: 支付请求头
 * @weixin PN15855012581
 * @date 12/30/2020 9:02 PM
 */
@Data
public class PayRequestHeader {

    // 支付类型 1支付宝 2微信 3三方支付公司 4四方支付
    private PayType payType;

    @NonNull
    // 商户ID
    private String merchantId;
    @NonNull
    // 应用ID
    private String appId;
}
