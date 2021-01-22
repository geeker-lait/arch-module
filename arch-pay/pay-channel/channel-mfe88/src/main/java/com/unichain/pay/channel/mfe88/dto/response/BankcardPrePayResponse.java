package com.unichain.pay.channel.mfe88.dto.response;

import lombok.Data;

@Data
public class BankcardPrePayResponse {
    //  商户号
    private String merchantNo;
    //处理结果代码	10	下单结果代码，为 10000 代表受理成功，否则受理失败，最终结果以代付查询为准详细参见 5.3 代码表
    private String dealCode;
    //处理结果信息	300	处理结果信息，如 dealCode 为10000 则信息为成功，其他代码则返回对应的错误信息
    private String dealMsg;
    //	签名数据	1024	对签名字符串进行校验
    private String sign;
    //  商户订单编号
    private String orderNo;
    //  订单号
    private String cxOrderNo;
    //  后续流程处理状态
    //  0：需要接收短信验证码，且需要调用快捷协议支付确认支付接口；
    //  1：不需要接收短信验证码，且需要调用快捷协议支付确认支付接口；
    //  3：不需要调用快捷协议支付确认支付接口；
    private Integer needSms;

}
