//package com.unichain.pay.yeepay.domain;
//
//import lombok.Data;
//
///**
// * 签约验证
// */
//@Data
//public class BankcardBindVerfyResponse extends BasePayResponse {
//
//    //商户编号 merchantno string(32) 原样返回
//    private String merchantno;
//    //绑卡请求号 requestno string(64) 原样返回
//    private String requestno;
//    //易宝流水号 yborderid string(64) 易宝平台生成的唯一流水号
//    private String yborderid;
//    //银行编码 bankcode string(16) 例如：ICBC 工商银行，详见银行编码列表
//    private String bankcode;
//    //卡号前六位 cardtop string(16)
//    private String cardtop;
//    //卡号后四位 cardlast string(16)
//    private String cardlast;
//    /**
//     * 订单状态 status string(32)
//     * BIND_SUCCESS：绑卡成功
//     * BIND_FAIL：绑卡失败
//     * BIND_ERROR：绑卡异常(可重试)
//     * TO_VALIDATE：待短验
//     * TIME_OUT：超时失败
//     * FAIL：系统异常
//     */
//    private String status;
//    //错误码 errorcode string(64)
//    private String errorcode;
//    //错误信息 errormsg string(128)
//    private String errormsg;
//}
