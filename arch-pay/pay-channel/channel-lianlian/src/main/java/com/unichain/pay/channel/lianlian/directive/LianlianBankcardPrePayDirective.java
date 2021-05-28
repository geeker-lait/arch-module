//package com.unichain.pay.channel.lianlian.directive;
//
//import com.unichain.pay.channel.lianlian.LianlianChannelDirecvite;
//import com.unichain.pay.channel.lianlian.LianlianPayRequestHandler;
//import com.unichain.pay.channel.lianlian.domain.BankcardPrePayParam;
//import com.unichain.pay.core.PayDirective;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.PayResponse;
//import org.springframework.stereotype.Service;
//
//@Service("LianlianBankcardPrePayDirective")
//public class LianlianBankcardPrePayDirective implements PayDirective<LianlianChannelDirecvite, BankcardPrePayParam> {
//
//    @Override
//    public PayResponse exec(LianlianChannelDirecvite channelDirective, BankcardPrePayParam bankcardPrePayParam, PayRequest payRequest) {
//        String data = LianlianPayRequestHandler.build(bankcardPrePayParam)
//                .sign(channelDirective.getPrivateKey())
//                .encrypt(channelDirective.getMerchantNo(), channelDirective.getSecretKey())
//                .exec(channelDirective.getDirectiveUri());
//        PayResponse payResponse = PayResponse.ok().data(data);
//        return payResponse;
//    }
//
//    @Override
//    public BankcardPrePayParam buildPayParam(PayRequest payRequest) {
//        BankcardPrePayParam bankcardPrePayParam = new BankcardPrePayParam();
//        LianlianPayRequestHandler.buildPayParam(bankcardPrePayParam, payRequest);
//        return bankcardPrePayParam;
//    }
//
//    @Override
//    public LianlianChannelDirecvite buildChannelDirective() {
//        return null;
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "LianlianBankcardPrePayDirective";
//    }
//}
