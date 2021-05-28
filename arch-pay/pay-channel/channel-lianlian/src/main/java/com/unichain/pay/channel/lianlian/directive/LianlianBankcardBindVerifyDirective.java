//package com.unichain.pay.channel.lianlian.directive;
//
//import com.unichain.pay.channel.lianlian.LianlianChannelDirecvite;
//import com.unichain.pay.channel.lianlian.LianlianPayRequestHandler;
//import com.unichain.pay.channel.lianlian.domain.BankcardBindVerifyParam;
//import com.unichain.pay.core.PayDirective;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.PayResponse;
//import org.springframework.stereotype.Service;
//
//
//@Service("LianlianBankcardBindVerifyDirective")
//public class LianlianBankcardBindVerifyDirective implements PayDirective<LianlianChannelDirecvite, BankcardBindVerifyParam> /*implements PayDirective<BankcardBindParam>*/ {
//
//    @Override
//    public PayResponse exec(LianlianChannelDirecvite channelDirective, BankcardBindVerifyParam bankcardBindVerifyParam, PayRequest payRequest) {
//        String privateKey = channelDirective.getPrivateKey();
//        String uri = channelDirective.getDirectiveUri();
//        LianlianPayRequestHandler.build(bankcardBindVerifyParam).sign(privateKey).exec(uri);
//        return null;
//    }
//
//    @Override
//    public BankcardBindVerifyParam buildPayParam(PayRequest payRequest) {
//        BankcardBindVerifyParam bankcardBindVerifyParam = new BankcardBindVerifyParam();
//        LianlianPayRequestHandler.buildPayParam(bankcardBindVerifyParam, payRequest);
//        return bankcardBindVerifyParam;
//    }
//
//    @Override
//    public LianlianChannelDirecvite buildChannelDirective() {
//        return null;
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "LianlianBankcardBindVerifyDirective";
//    }
//}
