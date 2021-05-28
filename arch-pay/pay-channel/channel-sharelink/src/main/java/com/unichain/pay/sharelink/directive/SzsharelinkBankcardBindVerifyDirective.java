//package com.unichain.pay.sharelink.directive;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.unichain.pay.core.*;
//import com.unichain.pay.core.util.RedisUtils;
//import com.unichain.pay.entity.ChannelDirectiveRecord;
//import com.unichain.pay.service.ChannelDirectiveRecordService;
//import com.unichain.pay.sharelink.SzsharelinkChannelDirecvite;
//import com.unichain.pay.sharelink.SzsharelinkPayRequestHandler;
//import com.unichain.pay.sharelink.domain.BankcardBindVerifyParam;
//import com.unichain.pay.sharelink.domain.BankcardBindVerifyResponse;
//import com.unichain.pay.sharelink.utils.ECTXmlUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import java.util.Map;
//
//@Service("SzsharelinkBankcardBindVerifyDirective")
//public class SzsharelinkBankcardBindVerifyDirective extends BaseRecord implements PayDirective<SzsharelinkChannelDirecvite, BankcardBindVerifyParam> {
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//
//    @Override
//    public PayResponse exec(SzsharelinkChannelDirecvite channelDirective, BankcardBindVerifyParam payParam, PayRequest payRequest) {
//        String uri = channelDirective.getDirectiveUri();
//        payParam.setMerchantId(channelDirective.getMerchantNo());
//        // 设置订单号
//        Map<String, Object> payParamMap = payRequest.getPayParamMap();
//        String smsSendNo = RedisUtils.getStr(payRequest.getAccountId() + "_shareLink_bindCard_smsSendNo");
//        smsSendNo = smsSendNo != null ? smsSendNo.replace("\"", "") : null;
//
//        payParam.setSmsSendNo(smsSendNo);
//
//        String data = SzsharelinkPayRequestHandler.build(payParam, channelDirective).exec(uri, ECTXmlUtil.CPREQ_SIREQ);
//
//        return record(payParam, payRequest, data);
//    }
//
//    @Override
//    public BankcardBindVerifyParam buildPayParam(PayRequest payRequest) {
//        BankcardBindVerifyParam param = new BankcardBindVerifyParam();
//        SzsharelinkPayRequestHandler.buildPayParam(param, payRequest);
//        return param;
//    }
//
//    @Override
//    public SzsharelinkChannelDirecvite buildChannelDirective() {
//        return new SzsharelinkChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "SzsharelinkBankcardBindVerifyDirective";
//    }
//
//    @Override
//    public PayResponse record(BankcardBindVerifyParam payParam, PayRequest payRequest, String data) {
//        ChannelDirectiveRecord save = super.createChannelDirectiveRecord(payRequest);
//        Map<String, String> resultMap = ECTXmlUtil.xmlToMap(data);
//        BankcardBindVerifyResponse response = BeanUtil.mapToBean(resultMap, BankcardBindVerifyResponse.class, true);
//        save.setDirectiveCode(getDirectiveCode());
//        save.setReturnCode(response.getRetFlag());
//        save.setReturnMsg(response.getResultMsg());
//        save.setPaymentId(payParam.getMerOrderId());
//        save.setChannelId(payRequest.getChannelId());
//        save.setProtocolId(response.getProtocolId());
//        save.setBankcard(payRequest.getBankcard());
//        save.setBizCode(BizCode.BINDCARD.val());
//        PayResponse result = new PayResponse();
//
//        PayResult payResult = new PayResult();
//        if (!"T".equals(response.getRetFlag())) {
//            // 0默认表示成功，1表示失败
//            save.setState(1);
//
//            payResult.setStatus(PayResultStatusEnum.FAIL);
//        } else {
//            boolean hasBindCard = false;
//            if (!StringUtils.isEmpty(response.getProtocolId())) {
//                save.setProtocolId(response.getProtocolId());
//                hasBindCard = true;
//            }
//
//            payResult.setStatus(PayResultStatusEnum.SUCCESS);
//            payResult.setPaymentId(payParam.getMerOrderId());
//            payResult.setHasBindCard(hasBindCard);
//        }
//        Integer code = getResultCode(response);
//        payResult.setCode(code);
//        payResult.setResultMsg(response.getResultMsg());
//        result.data(payResult);
//        channelDirectiveRecordService.save(save);
//        return result;
//    }
//
//    public Integer getResultCode(BankcardBindVerifyResponse response) {
//        Integer code = 0;
//        //  手机号码与预留时不符
//        if (response.getResultCode().equals("1127") || response.getResultMsg().contains("与签约行预留手机号不符")) {
//            PayStateCode.NOT_BANK_PHONE.code();
//        } else if (response.getResultMsg().contains("账户可用余额不足") || response.getResultMsg().contains("账户余额不足支付")){
//            PayStateCode.NOT_SUFFICIENT_FUNDS.code();
//        } else if (response.getResultMsg().contains("无可用路由")){
//            PayStateCode.NOT_SUPPORT_BANKCARD.code();
//        }
//
//        return code;
//    }
//}
