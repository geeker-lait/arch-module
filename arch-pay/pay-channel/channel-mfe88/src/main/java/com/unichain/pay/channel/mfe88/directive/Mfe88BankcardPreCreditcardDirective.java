//package com.unichain.pay.channel.mfe88.directive;
//
//import com.unichain.pay.channel.mfe88.Mfe88ChannelDirecvite;
//import com.unichain.pay.channel.mfe88.Mfe88PayRequestHandler;
//import com.unichain.pay.channel.mfe88.dto.request.PrePayingParams;
//import org.arch.payment.sdk.PayRequest;
//import org.arch.payment.sdk.PayResponse;
//import org.springframework.stereotype.Service;
//
//@Service("Mfe88BankcardPrePayCreditcardDirective")
//public class Mfe88BankcardPreCreditcardDirective implements PayDirective<Mfe88ChannelDirecvite, PrePayingParams> {
//
//
//    @Override
//    public PayResponse exec(Mfe88ChannelDirecvite channelDirective, PrePayingParams prePayingRequest, PayRequest payRequest) {
//        Mfe88PayRequestHandler.build(prePayingRequest, channelDirective)
////                .sign(channelDirective.getPrivateKey())
////                .encrypt(channelDirective.getMerchantNo(),channelDirective.getSecretKey());
//                .exec(channelDirective.getDirectiveUri());
//        return null;
//    }
//
//    @Override
//    public PrePayingParams buildPayParam(PayRequest payRequest) {
//        PrePayingParams prePayingRequest = new PrePayingParams();
//        Mfe88PayRequestHandler.buildPayParam(prePayingRequest, payRequest);
//        return prePayingRequest;
//    }
//
//    @Override
//    public Mfe88ChannelDirecvite buildChannelDirective() {
//        return new Mfe88ChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "Mfe88BankcardPrePayCreditcardDirective";
//    }
//}
