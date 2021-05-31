//package com.unichain.pay.channel.mfe88.directive;
//
//import cn.hutool.json.JSONUtil;
//import com.unichain.pay.channel.mfe88.Mfe88ChannelDirecvite;
//import com.unichain.pay.channel.mfe88.Mfe88PayRequestHandler;
//import com.unichain.pay.channel.mfe88.dto.response.BankcardBindResponse;
//import com.unichain.pay.channel.mfe88.dto.request.BindCardParams;
//import org.arch.payment.sdk.PayRequest;
//import org.arch.payment.sdk.PayResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//@Service("Mfe88BankcardBindVerifyDirective")
//public class Mfe88BankcardBindVerifyDirective implements PayDirective<Mfe88ChannelDirecvite, BindCardParams> /*implements PayDirective<BankcardBindParam>*/ {
//
//
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(Mfe88ChannelDirecvite channelDirective, BindCardParams bindCardRequest, PayRequest payRequest) {
//
//        String uri = channelDirective.getDirectiveUri();
////        String merchantNo = channelDirective.getMerchantNo();
////        String publicKey = channelDirective.getPublicKey();
////        String privateKey = channelDirective.getPrivateKey();
//        bindCardRequest.setMerchantNo(channelDirective.getMerchantNo());
//        String data = Mfe88PayRequestHandler.build(bindCardRequest, channelDirective).exec(uri);
//        return record(bindCardRequest, payRequest, data);
//    }
//
//    @Override
//    public BindCardParams buildPayParam(PayRequest payRequest) {
//        BindCardParams bindCardRequest = new BindCardParams();
//        Mfe88PayRequestHandler.buildPayParam(bindCardRequest, payRequest);
//        return bindCardRequest;
//    }
//
//    @Override
//    public Mfe88ChannelDirecvite buildChannelDirective() {
//        return new Mfe88ChannelDirecvite();
//    }
//
//    @Override
//    public PayResponse record(BindCardParams payParam, PayRequest payRequest, String data) {
//        BankcardBindResponse response = JSONUtil.toBean(data, BankcardBindResponse.class);
//
//        ChannelDirectiveRecord save = new ChannelDirectiveRecord();
//        save.setAccountId(payRequest.getAccountId());
//        save.setAppId(payRequest.getAppId());
//        save.setUserId(payRequest.getUserId());
//        save.setDirectiveCode(getDirectiveCode());
//        save.setCompanyId(payRequest.getCompanyId());
//        save.setChannelId(payRequest.getChannelId());
//        save.setReturnCode(response.getDealCode());
//        save.setReturnMsg(response.getDealMsg());
//        save.setPaymentId(payParam.getOrderNo());
//        save.setBizCode(BizCode.BINDCARD.val());
//
//        //Map resultMap = new HashMap();
//        PayResponse result = new PayResponse();
//
//        PayResult payResult = new PayResult();
//        boolean hasBindCard = false;
//        if (response.getDealMsg().contains("不允许重复确认绑定!") && "90001".equals(response.getDealCode())) {
//            hasBindCard = true;
//            payResult.setStatus(PayResultStatusEnum.SUCCESS);
//            payResult.setHasBindCard(hasBindCard);
//        } else if (!"10000".equals(response.getDealCode())) {
//            // 0默认表示成功，1表示失败
//            save.setState(1);
//            payResult.setStatus(PayResultStatusEnum.FAIL);
//        } else {
//
//            payResult.setStatus(PayResultStatusEnum.SUCCESS);
//            payResult.setHasBindCard(hasBindCard);
//            payResult.setPaymentId(payParam.getOrderNo());
//        }
//
//        Integer code = getResultCode(response);
//        payResult.setCode(code);
//        payResult.setResultMsg(response.getDealMsg());
//        result.data(payResult);
//        channelDirectiveRecordService.save(save);
//        return result;
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "Mfe88BankcardBindVerifyDirective";
//    }
//
//    public Integer getResultCode(BankcardBindResponse response) {
//        Integer code = 0;
//        if ("90001".equals(response.getDealCode())) {
//            if ((response.getDealMsg().contains("账户可用余额不足") || response.getDealMsg().contains("账户余额不足"))) {
//                code = PayStateCode.NOT_SUFFICIENT_FUNDS.code();
//            }
//            if ((response.getDealMsg().contains("上送的手机号或身份证号与已签约信息不一致") || response.getDealMsg().contains("请求中预留手机号与签约人在付款行预留的协议支付手机号不符"))) {
//                code = PayStateCode.NOT_BANK_PHONE.code();
//            }
//            if (response.getDealMsg().contains("通道不支持的产品或设置不正确")) {
//                code = PayStateCode.NOT_SUPPORT_BANKCARD.code();
//            }
//        } else if ("20029".equals(response.getDealCode()) && response.getDealMsg().equals("余额不足")) {
//            code = PayStateCode.NOT_SUFFICIENT_FUNDS.code();
//        }
//        return code;
//    }
//}
