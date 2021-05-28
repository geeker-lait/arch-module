//package com.unichain.pay.yeepay.domain;
//
//import lombok.Data;
//
///**
// * 签约支付验证
// */
//@Data
//public class BankcardBindResponse extends BasePayResponse {
//
//    //商户编号 merchantno string(32) 原样返回
//    private String merchantno;
//
//    //业务请求号 requestno string(64) 原样返回
//    private String requestno;
//
//    //易宝流水号 yborderid string(64) 易宝平台生成的唯一流水号
//    private String yborderid;
//    //订单状态 status string(32) BIND_SUCCESS  ： 绑 卡 成 功 (  当 issm=false  时返回)TO_VALIDATE：待短验 BIND_FAIL：绑卡失败 BIND_ERROR：绑卡异常(可重试)TIME_OUT：超时失败 FAIL：系统异常
//    private String status;
//    //是否发送短验 issms string(16)
//    private String issms;
//    //银行编码 bankcode string
//    private String bankcode;
//    //短验码 smscode string(32) 商户发短验时返回的易宝生成的短验码（易宝发短验时此字段为空）
//    private String smscode;
//    //短验发送方 codesender string(32) CUSTOMER：商户发送 YEEPAY：易宝发送 BANK：银行发送 NONE：无短验
//    private String codesender;
//    //实际短验发送类型 smstype string(32) VOICE：语音 MESSAGE：短信
//    private String smstype;
//    //鉴权类型 authtype string(16)
//    private String authtype;
//    //卡号前六位 cardtop string(16)
//    private String cardtop;
//    //卡号后四位 cardlast string(16)
//    private String cardlast;
//    //备注 remark string(42)
//    private String remark;
//    //错误码 errorcode string(64)
//    private String errorcode;
//    //错误信息 errormsg string(128)
//    private String errormsg;
//
//    private String state;
//
//}
