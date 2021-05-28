//package com.unichain.pay.channel.mfe88.directive;
//
//import com.alibaba.fastjson.JSONObject;
//import com.unichain.pay.channel.mfe88.Mfe88ChannelDirecvite;
//import com.unichain.pay.channel.mfe88.Mfe88PayRequestHandler;
//import com.unichain.pay.channel.mfe88.dto.request.TransterRequest;
//import com.unichain.pay.channel.mfe88.dto.response.PayForAnotherResponse;
//import com.unichain.pay.channel.mfe88.utils.Base64;
//import org.arch.payment.sdk.PayRequest;
//import org.arch.payment.sdk.PayResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//
///**
// * @Author lait.zhang@gmail.com
// * @Tel 15801818092
// * @Date 12/3/2019
// * @Description 代付指令
// */
//@Service("Mfe88PayForAnotherDirective")
//public class Mfe88ForAnotherDirective implements PayDirective<Mfe88ChannelDirecvite, TransterRequest> {
//
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(Mfe88ChannelDirecvite channelDirective, TransterRequest payParam, PayRequest payRequest) {
//        payParam.setMerchantNo(channelDirective.getMerchantNo());
//
//        String result = Mfe88PayRequestHandler.build(payParam, channelDirective)
////                .sign(channelDirective.getPrivateKey())
////                .encrypt(channelDirective.getMerchantNo(), channelDirective.getSecretKey())
//                .exec(channelDirective.getDirectiveUri());
//        return record(payParam, payRequest, result);
//    }
//
//    @Override
//    public PayResponse record(TransterRequest transterRequest, PayRequest payRequest, String data) {
//        // 保存记录
//        ChannelDirectiveRecord channelDirectiveRecord = new ChannelDirectiveRecord();
//        channelDirectiveRecord.setAccountId(payRequest.getAccountId());
//        channelDirectiveRecord.setAppId(payRequest.getAppId());
//        channelDirectiveRecord.setPaymentId(payRequest.getPaymentId());
//        channelDirectiveRecord.setChannelId(payRequest.getChannelId());
//        channelDirectiveRecord.setUserId(payRequest.getUserId());
//        channelDirectiveRecord.setCompanyId(payRequest.getCompanyId());
//        channelDirectiveRecord.setPaymentId(payRequest.getPaymentId());
//        channelDirectiveRecord.setDirectiveCode(getDirectiveCode());
//        try {
//            channelDirectiveRecord.setBankcard(Base64.decode(transterRequest.getAccountNo()));
//            // 设置金额
//            channelDirectiveRecord.setAmount(new BigDecimal(AmountUtils.changeF2Y(transterRequest.getOrderAmount())));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        PayForAnotherResponse payForAnotherResponse = JSONObject.parseObject(data, PayForAnotherResponse.class);
//        String returnCode = payForAnotherResponse.getDealCode();
//        channelDirectiveRecord.setReturnCode(returnCode);
//        channelDirectiveRecord.setReturnMsg(payForAnotherResponse.getDealMsg());
//        //下单结果代码，为 10000 代表受理成功，否则受理失败，最终结果以代付查询为准详细参见 5.3 代码表
//        PayResponse result = new PayResponse();
//        //下单结果代码，为 10000 代表受理成功，否则受理失败，最终结果以代付查询为准详细参见 5.3 代码表
//        if (!returnCode.equals("10000")) {
//            // 0默认表示成功，1表示失败
//            channelDirectiveRecord.setState(1);
//        } else {
//            result.data(payRequest.getPaymentId());
//        }
//        channelDirectiveRecordService.save(channelDirectiveRecord);
//        return result;
//
//    }
//
//    @Override
//    public TransterRequest buildPayParam(PayRequest payRequest) {
//        TransterRequest transterRequest = new TransterRequest();
//        Mfe88PayRequestHandler.buildPayParam(transterRequest, payRequest);
//        return transterRequest;
//    }
//
//    @Override
//    public Mfe88ChannelDirecvite buildChannelDirective() {
//        return new Mfe88ChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "Mfe88PayForAnotherDirective";
//    }
//}
