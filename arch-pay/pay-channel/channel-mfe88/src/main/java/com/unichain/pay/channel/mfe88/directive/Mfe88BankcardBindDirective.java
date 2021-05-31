//package com.unichain.pay.channel.mfe88.directive;
//
//import cn.hutool.json.JSONUtil;
//import com.unichain.pay.channel.mfe88.Mfe88ChannelDirecvite;
//import com.unichain.pay.channel.mfe88.Mfe88PayRequestHandler;
//import com.unichain.pay.channel.mfe88.dto.request.PreBindCardParams;
//import com.unichain.pay.channel.mfe88.dto.response.BankcardBindResponse;
//import org.arch.payment.sdk.Directive;
//import org.arch.payment.sdk.PayRequest;
//import org.arch.payment.sdk.PayResponse;
//import org.arch.payment.sdk.directive.BindCardDirective;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//@Service("Mfe88BankcardBindDirective")
//public class Mfe88BankcardBindDirective implements BindCardDirective,Directive<Mfe88ChannelDirecvite, PreBindCardParams> {
//
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(Mfe88ChannelDirecvite channelDirective, PreBindCardParams preBindCardRequest, PayRequest payRequest) {
//// 测试
////        PayResponse result = new PayResponse();
////        Map resultMap = new HashMap();
////        resultMap.put("status","FAIL");
////        result.data(resultMap);
////        return result;
//
//        preBindCardRequest.setMerchantNo(channelDirective.getMerchantNo());
//        String uri = channelDirective.getDirectiveUri();
//        preBindCardRequest.setOrderNo(payRequest.getPaymentId());
//        // 响应结果
//        String data = Mfe88PayRequestHandler.build(preBindCardRequest, channelDirective).exec(uri);
//        // 解密处理
//        preBindCardRequest.decrypt(channelDirective.getSecretKey());
//        return record(preBindCardRequest, payRequest, data);
//    }
//
//    /**
//     * 记录返回结果
//     *
//     * @param payParam
//     * @param payRequest
//     * @param data
//     */
//    @Override
//    public PayResponse record(PreBindCardParams payParam, PayRequest payRequest, String data) {
//        BankcardBindResponse response = JSONUtil.toBean(data, BankcardBindResponse.class);
//
//        ChannelDirectiveRecord save = new ChannelDirectiveRecord();
//        save.setAccountId(payRequest.getAccountId());
//        save.setAppId(payRequest.getAppId());
//        save.setUserId(payRequest.getUserId());
//        save.setBankcard(payParam.getBankCardNo());
//        save.setChannelId(payRequest.getChannelId());
//        //save.setChannelCode(payParam.getPayChannelCode());
//        save.setBankCode(payParam.getPayChannelCode());
//        save.setDirectiveCode(getDirectiveCode());
//        save.setCompanyId(payRequest.getCompanyId());
//        save.setPhone(payParam.getPhone());
//        // 订单号
//        save.setPaymentId(payRequest.getPaymentId());
//        save.setBizCode(BizCode.BINDCARD.val());
//        save.setReturnCode(response.getDealCode());
//        save.setReturnMsg(response.getDealMsg());
//        //Map resultMap = new HashMap();
//        PayResponse result = new PayResponse();
//        PayResult payResult = new PayResult();
//        boolean hasBindCard = false;
//        if (response.getDealMsg().contains("签约成功，重复签约") && "90001".equals(response.getDealCode())) {
//            hasBindCard = true;
//            payResult.setStatus(PayResultStatusEnum.SUCCESS);
//            payResult.setHasBindCard(hasBindCard);
//            payResult.setPaymentId(payRequest.getPaymentId());
//        } else if (!"10000".equals(response.getDealCode())) {
//            // 0默认表示成功，1表示失败
//            save.setState(1);
//            payResult.setStatus(PayResultStatusEnum.FAIL);
//        } else {
//
//            payResult.setStatus(PayResultStatusEnum.SUCCESS);
//            payResult.setHasBindCard(hasBindCard);
//            payResult.setPaymentId(payRequest.getPaymentId());
//        }
//        Integer code = getResultCode(response);
//        payResult.setCode(code);
//        payResult.setResultMsg(response.getDealMsg());
//        result.data(payResult);
//        channelDirectiveRecordService.save(save);
//        return result;
//    }
//
//    @Override
//    public PreBindCardParams buildPayParam(PayRequest payRequest) {
//        PreBindCardParams preBindCardRequest = new PreBindCardParams();
//        Mfe88PayRequestHandler.buildPayParam(preBindCardRequest, payRequest);
//        return preBindCardRequest;
//    }
//
//    @Override
//    public Mfe88ChannelDirecvite buildChannelDirective() {
//        return new Mfe88ChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "Mfe88BankcardBindDirective";
//    }
//
//    public Integer getResultCode(BankcardBindResponse response){
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
