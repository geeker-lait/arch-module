//package com.unichain.pay.yeepay.directive;
//
//import com.unichain.pay.core.PayDirective;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.PayResponse;
//import com.unichain.pay.yeepay.YeepayChannelDirecvite;
//import com.unichain.pay.yeepay.YeepayPayRequestHandler;
//import com.unichain.pay.yeepay.domain.YeepayPrePayParam;
//import org.springframework.stereotype.Service;
//
//@Service("YeepayPrePayCreditcardDirective")
//public class YeepayPrePayCreditcardDirective implements PayDirective<YeepayChannelDirecvite, YeepayPrePayParam> {
//
//
//    @Override
//    public PayResponse exec(YeepayChannelDirecvite channelDirective, YeepayPrePayParam bankcardPrePayParam, PayRequest payRequest) {
//        YeepayPayRequestHandler.build(bankcardPrePayParam)
//                .exec(channelDirective);
//        return null;
//    }
//
//    @Override
//    public YeepayPrePayParam buildPayParam(PayRequest payRequest) {
//        YeepayPrePayParam bankcardPrePayParam = new YeepayPrePayParam();
//        YeepayPayRequestHandler.buildPayParam(bankcardPrePayParam, payRequest);
//        return bankcardPrePayParam;
//    }
//
//    @Override
//    public YeepayChannelDirecvite buildChannelDirective() {
//        return new YeepayChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "YeepayPrePayCreditcardDirective";
//    }
//}
