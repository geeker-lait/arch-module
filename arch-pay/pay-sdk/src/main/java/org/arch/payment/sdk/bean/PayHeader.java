package org.arch.payment.sdk.bean;

import lombok.Data;
import lombok.NonNull;
import org.arch.payment.api.enums.PayType;

/**
 * @author lait.zhang@gmail.com
 * @description: 支付请求头
 * @weixin PN15855012581
 * @date 12/30/2020 9:02 PM
 */
@Data
public class PayHeader {

    // 支付类型 1支付宝 2微信 3三方支付公司 4四方支付
    private PayType payType;
    @NonNull
    // 商户号
    private String merchantNo;
    @NonNull
    // 应用ID
    private String appId;
}
