//package com.unichain.pay.channel.lianlian.directive;
//
//import com.unichain.pay.channel.lianlian.LianlianChannelDirecvite;
//import com.unichain.pay.channel.lianlian.LianlianPayRequestHandler;
//import com.unichain.pay.channel.lianlian.domain.BankcardBindParam;
//
//
//@Service("LianlianBankcardBindDirective")
//public class LianlianBankcardBindDirective implements PayDirective<LianlianChannelDirecvite, BankcardBindParam> {
//
//    @Override
//    public PayResponse exec(LianlianChannelDirecvite channelDirective, BankcardBindParam bankcardBindParam, PayRequest payRequest) {
//        String privateKey = channelDirective.getPrivateKey();
//        String oidPartner = channelDirective.getMerchantNo();
//        String publicKey = channelDirective.getPublicKey();
//        //银通公钥隐藏
//        String ytPublicKey = channelDirective.getSecretKey();
//        String uri = channelDirective.getDirectiveUri();
//        // 响应结果
//        String data = LianlianPayRequestHandler.build(bankcardBindParam).sign(privateKey).encrypt(oidPartner, ytPublicKey).exec(uri);
//
//        PayResponse payResponse = PayResponse.ok().data(data);
//        //PR bankcardBindResponse = LianlianPayResponseHandler.build(new BankcardBindResponse()).data().
//        return payResponse;
//    }
//
//
//    @Override
//    public BankcardBindParam buildPayParam(PayRequest payRequest) {
//        BankcardBindParam bankcardBindParam = new BankcardBindParam();
//        LianlianPayRequestHandler.buildPayParam(bankcardBindParam, payRequest);
//        return bankcardBindParam;
//    }
//
//    @Override
//    public LianlianChannelDirecvite buildChannelDirective() {
//        return new LianlianChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "LianlianBankcardBindDirective";
//    }
//
//
//}
