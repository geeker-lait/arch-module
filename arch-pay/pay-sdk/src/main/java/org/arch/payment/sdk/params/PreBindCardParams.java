package org.arch.payment.sdk.params;


import lombok.Data;
import org.arch.payment.sdk.bean.PayFourElements;
import org.arch.payment.sdk.bean.PayHeader;
import org.arch.payment.sdk.PayParams;

/**
 * @author lait.zhang@gmail.com
 * @description: 绑卡四要素请求
 * @weixin PN15855012581
 * @date 12/30/2020 7:32 PM
 */
@Data
public class PreBindCardParams implements PayParams {
    // 支付请求头
    private PayHeader payHeader;
    // 绑卡四要素
    private PayFourElements bindCardElements;


}
