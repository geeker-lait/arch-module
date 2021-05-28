//package com.unichain.pay.channel.mfe88.directive;
//
//import com.alibaba.fastjson.JSONObject;
//import com.unichain.pay.channel.mfe88.Mfe88ChannelDirecvite;
//import com.unichain.pay.channel.mfe88.Mfe88PayRequestHandler;
//import com.unichain.pay.channel.mfe88.demo.Demo;
//import com.unichain.pay.channel.mfe88.dto.request.PrePayingRequest;
//import com.unichain.pay.channel.mfe88.dto.response.BankcardPrePayResponse;
//import org.arch.payment.sdk.PayRequest;
//import org.arch.payment.sdk.PayResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//
//@Service("Mfe88BankcardPrePayDirective")
//public class Mfe88BankcardPreDirective implements PayDirective<Mfe88ChannelDirecvite, PrePayingRequest> {
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(Mfe88ChannelDirecvite channelDirective, PrePayingRequest prePayingRequest, PayRequest payRequest) {
//        prePayingRequest.setMerchantNo(channelDirective.getMerchantNo());
//
//        String data = Mfe88PayRequestHandler.build(prePayingRequest, channelDirective)
//                /* .sign(channelDirective.getPrivateKey())
//                 .encrypt(channelDirective.getMerchantNo(), channelDirective.getSecretKey())*/
//                .exec(channelDirective.getDirectiveUri());
//        System.out.println("responseData:" + data);
//        return record(prePayingRequest, payRequest, data);
//
//    }
//
//    @Override
//    public PrePayingRequest buildPayParam(PayRequest payRequest) {
//        PrePayingRequest prePayingRequest = new PrePayingRequest();
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
//        return "Mfe88BankcardPrePayDirective";
//    }
//
//    @Override
//    public PayResponse record(PrePayingRequest prePayingRequest, PayRequest payRequest, String data) {
//        // 保存记录
//        ChannelDirectiveRecord channelDirectiveRecord = new ChannelDirectiveRecord();
//
//        channelDirectiveRecord.setAccountId(payRequest.getAccountId());
//        channelDirectiveRecord.setAppId(payRequest.getAppId());
//        channelDirectiveRecord.setChannelId(payRequest.getChannelId());
//        channelDirectiveRecord.setUserId(payRequest.getUserId());
//        channelDirectiveRecord.setCompanyId(payRequest.getCompanyId());
//        channelDirectiveRecord.setDirectiveCode(getDirectiveCode());
//        //String aesKey = "191e257a18e6439ab8a7573f0b278394";   //test
//        String aesKey = "84ae078cd7ab4fe4a0727e1ede7512d5";     //pord
//        channelDirectiveRecord.setBankcard(Demo.AESDncode2(prePayingRequest.getBankCardNo(), aesKey));
//        channelDirectiveRecord.setPaymentId(payRequest.getPaymentId());
//        channelDirectiveRecord.setChannelId(payRequest.getChannelId());
//        BankcardPrePayResponse bankcardPrePayResponse = JSONObject.parseObject(data, BankcardPrePayResponse.class);
//        String returnCode = bankcardPrePayResponse.getDealCode();
//        channelDirectiveRecord.setReturnCode(returnCode);
//        channelDirectiveRecord.setReturnMsg(bankcardPrePayResponse.getDealMsg());
//        try {
//            String orderAmount = AmountUtils.changeF2Y(prePayingRequest.getOrderAmount());
//            channelDirectiveRecord.setAmount(new BigDecimal(orderAmount));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        channelDirectiveRecord.setBizCode(BizCode.PAY.val());
//        PayResponse result = new PayResponse();
//        PayResult payResult = new PayResult();
//        //下单结果代码，为 10000 代表受理成功，否则受理失败，最终结果以代付查询为准详细参见 5.3 代码表
//        if (!returnCode.equals("10000")) {
//            // 0默认表示成功，1表示失败
//            channelDirectiveRecord.setState(1);
//            payResult.setStatus(PayResultStatusEnum.FAIL);
//        } else {
//
//            //  0：需要接收短信验证码，且需要调用快捷协议支付确认支付接口；
//            //  1：不需要接收短信验证码，且需要调用快捷协议支付确认支付接口；
//            //  3：不需要调用快捷协议支付确认支付接口；
//            payResult.setStatus(PayResultStatusEnum.SUCCESS);
//            payResult.setPaymentId(payRequest.getPaymentId());
//            payResult.setNeedSms(bankcardPrePayResponse.getNeedSms());
//
//        }
//        Integer code = getResultCode(bankcardPrePayResponse);
//        payResult.setCode(code);
//        payResult.setResultMsg(bankcardPrePayResponse.getDealMsg());
//        result.data(payResult);
//        channelDirectiveRecordService.save(channelDirectiveRecord);
//        return result;
//    }
//
//    public Integer getResultCode(BankcardPrePayResponse response){
//        Integer code = 0;
//        if ("90001".equals(response.getDealCode())) {
//            if ((response.getDealMsg().contains("账户可用余额不足") || response.getDealMsg().contains("账户余额不足"))) {
//                code = PayStateCode.NOT_SUFFICIENT_FUNDS.code();
//            }
//            if ((response.getDealMsg().contains("上送的手机号或身份证号与已签约信息不一致") || response.getDealMsg().contains("请求中预留手机号与签约人在付款行预留的协议支付手机号不符"))) {
//                code = PayStateCode.NOT_BANK_PHONE.code();
//            }
//            if (response.getDealMsg().contains("通道不支持的产品或设置不正确")){
//                code = PayStateCode.NOT_SUPPORT_BANKCARD.code();
//            }
//        } else if ("20029".equals(response.getDealCode()) && response.getDealMsg().equals("余额不足")) {
//            code = PayStateCode.NOT_SUFFICIENT_FUNDS.code();
//        }
//        return code;
//    }
//}
