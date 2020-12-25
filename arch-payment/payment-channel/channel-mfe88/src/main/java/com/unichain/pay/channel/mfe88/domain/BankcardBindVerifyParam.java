package com.unichain.pay.channel.mfe88.domain;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import org.arch.payment.sdk.PayParam;
import org.arch.payment.sdk.PayRequest;

import java.util.Map;

/**
 * 签约申请验证 请求
 *
 * @date
 */
@Data
public class BankcardBindVerifyParam implements PayParam {
    private String service = "quickPayBindConfirm";

    private String merchantNo;

    private String version = "V3.0";

    private Integer orderSource = 2;

    private String orderNo;

    private String smsCode;

    private String signVersion = "V3.0";

    private String riskVersion = "V3.0";

    private String sign;

    private Integer signType = 3;

    @Override
    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
        BankcardBindVerifyParam bean = BeanUtil.mapToBean(map, BankcardBindVerifyParam.class, true);
        BeanUtil.copyProperties(bean, this);
        this.orderNo = payRequest.getPayParamMap().get("paymentId").toString();
        return this;
    }

    @Override
    public PayParam encrypt(String encryptKey) {
        return this;
    }
}
