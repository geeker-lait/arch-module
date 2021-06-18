//package com.unichain.pay.channel.mfe88.directive;
//
//import cn.hutool.json.JSONUtil;
//import com.unichain.pay.channel.mfe88.Mfe88ChannelDirecvite;
//import com.unichain.pay.channel.mfe88.Mfe88PayRequestHandler;
//import com.unichain.pay.channel.mfe88.dto.request.RefundRequest;
//import com.unichain.pay.channel.mfe88.dto.response.RefundOrderResponse;
//import org.arch.payment.sdk.PayRequest;
//import org.arch.payment.sdk.PayResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * @Author lait.zhang@gmail.com
// * @Tel 15801818092
// * @Date 12/4/2019
// * @Description ${Description}
// */
//@Service("Mfe88RefundOrderDirective")
//public class Mfe88RefundOrderDirective implements PayDirective<Mfe88ChannelDirecvite, RefundRequest> {
//
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(Mfe88ChannelDirecvite channelDirective, RefundRequest payParam, PayRequest payRequest) {
////        String privateKey = channelDirective.getPrivateKey();
//        String oidPartner = channelDirective.getMerchantNo();
////        String publicKey = channelDirective.getPublicKey();
//        payParam.setMerchantNo(oidPartner);
////        //银通公钥隐藏
////        String ytPublicKey = channelDirective.getSecretKey();
//        String uri = channelDirective.getDirectiveUri();
//        // 响应结果
//        String data = Mfe88PayRequestHandler.build(payParam, channelDirective)
//                /*.sign(privateKey).encrypt(oidPartner,ytPublicKey)*/
//                .exec(uri);
//        record(payParam, payRequest, data);
//        PayResponse payResponse = new PayResponse();
//        return payResponse.data(data);
//    }
//
//    @Override
//    public RefundRequest buildPayParam(PayRequest payRequest) {
//        RefundRequest refundRequest = new RefundRequest();
//        Mfe88PayRequestHandler.buildPayParam(refundRequest, payRequest);
//        return refundRequest;
//    }
//
//    @Override
//    public Mfe88ChannelDirecvite buildChannelDirective() {
//        return new Mfe88ChannelDirecvite();
//    }
//
//    @Override
//    public PayResponse record(RefundRequest payParam, PayRequest payRequest, String data) {
//        RefundOrderResponse response = JSONUtil.toBean(data, RefundOrderResponse.class);
//
//        ChannelDirectiveRecord save = new ChannelDirectiveRecord();
//        save.setAccountId(payRequest.getAccountId());
//        save.setAppId(payRequest.getAppId());
//        save.setUserId(payRequest.getUserId());
//        save.setChannelId(payRequest.getChannelId());
//        save.setDirectiveCode(getDirectiveCode());
//        save.setCompanyId(payRequest.getCompanyId());
//        save.setReturnCode(response.getDealCode());
//        save.setReturnMsg(response.getDealMsg());
//        save.setPaymentId(payRequest.getPaymentId());
//
//        PayResponse result = new PayResponse();
//        if (!"10000".equals(response.getDealCode())) {
//            // 0默认表示成功，1表示失败
//            save.setState(1);
//        } else {
//            result.data(payRequest.getPaymentId());
//        }
//        channelDirectiveRecordService.save(save);
//        return result;
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "Mfe88RefundOrderDirective";
//    }
//}
