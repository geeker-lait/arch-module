//package com.unichain.pay.sharelink.directive;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.unichain.pay.core.*;
//import com.unichain.pay.core.util.RedisUtils;
//import com.unichain.pay.entity.ChannelDirectiveRecord;
//import com.unichain.pay.service.ChannelDirectiveRecordService;
//import com.unichain.pay.sharelink.SzsharelinkChannelDirecvite;
//import com.unichain.pay.sharelink.SzsharelinkPayRequestHandler;
//import com.unichain.pay.sharelink.domain.BankcardBindParam;
//import com.unichain.pay.sharelink.domain.BankcardBindResponse;
//import com.unichain.pay.sharelink.utils.ECTXmlUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import java.util.Map;
//
//@Service("SzsharelinkBankcardBindDirective")
//public class SzsharelinkBankcardBindDirective extends BaseRecord implements PayDirective<SzsharelinkChannelDirecvite, BankcardBindParam> {
//
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(SzsharelinkChannelDirecvite channelDirective, BankcardBindParam payParam, PayRequest payRequest) {
//        payParam.setMerchantId(channelDirective.getMerchantNo());
//
//        String uri = channelDirective.getDirectiveUri();
//        String data = SzsharelinkPayRequestHandler.build(payParam, channelDirective).exec(uri, ECTXmlUtil.CPREQ_QIAREQ);
//
//        return record(payParam, payRequest, data);
//    }
//
//    @Override
//    public BankcardBindParam buildPayParam(PayRequest payRequest) {
//        BankcardBindParam bankcardBindParam = new BankcardBindParam();
//        SzsharelinkPayRequestHandler.buildPayParam(bankcardBindParam, payRequest);
//        return bankcardBindParam;
//    }
//
//    @Override
//    public SzsharelinkChannelDirecvite buildChannelDirective() {
//        return new SzsharelinkChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "SzsharelinkBankcardBindDirective";
//    }
//
//    @Override
//    public PayResponse record(BankcardBindParam payParam, PayRequest payRequest, String data) {
//        Map<String, String> stringStringMap = ECTXmlUtil.xmlToMap(data);
//        BankcardBindResponse response = BeanUtil.mapToBean(stringStringMap, BankcardBindResponse.class, true);
//
//        ChannelDirectiveRecord save = super.createChannelDirectiveRecord(payRequest);
//        save.setBankcard(payParam.getAccount());
//        save.setPhone(payParam.getMobile());
//        save.setDirectiveCode(getDirectiveCode());
//        save.setPaymentId(payRequest.getPaymentId());
//        save.setChannelId(payRequest.getChannelId());
//        save.setReturnCode(response.getRetFlag());
//        save.setReturnMsg(response.getResultMsg());
//        save.setBizCode(BizCode.BINDCARD.val());
//        PayResponse result = new PayResponse();
//
//        PayResult payResult = new PayResult();
//        if (!"T".equalsIgnoreCase(response.getRetFlag())) {
//            // 0默认表示成功，1表示失败
//            save.setState(1);
//            /*if (response.getResultCode().contains("1127")) {
//
//                payResult.setCode(10000000);
//            }*/
//
//            payResult.setStatus(PayResultStatusEnum.FAIL);
//        } else {
//            boolean hasBindCard = false;
//            //  该客户的银行账号在本次签约发短信之前已经签约成功的情况下直接返回签约协议号, 否则发送验证码
//            if (!StringUtils.isEmpty(response.getSmsSendNo())) {
//                String key = payRequest.getAccountId() + "_shareLink_bindCard_smsSendNo";
//                RedisUtils.set(key, response.getSmsSendNo(), 10 * 60);
//
//            } else if (!StringUtils.isEmpty(response.getProtocolId())) {
//                save.setProtocolId(response.getProtocolId());
//                hasBindCard = true;
//            }
//
//            payResult.setStatus(PayResultStatusEnum.SUCCESS);
//            payResult.setPaymentId(payRequest.getPaymentId());
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
//    public Integer getResultCode(BankcardBindResponse response) {
//        Integer code = 0;
//        //  手机号码与预留时不符
//        if (response.getResultCode().equals("1127") || response.getResultMsg().contains("与签约行预留手机号不符")) {
//            PayStateCode.NOT_BANK_PHONE.code();
//        } else if (response.getResultMsg().contains("账户可用余额不足") || response.getResultMsg().contains("账户余额不足支付")) {
//            PayStateCode.NOT_SUFFICIENT_FUNDS.code();
//        } else if (response.getResultMsg().contains("无可用路由")) {
//            PayStateCode.NOT_SUPPORT_BANKCARD.code();
//        }
//
//        return code;
//    }
//}
