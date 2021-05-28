//package com.unichain.pay.yeepay.directive;
//
//import cn.hutool.json.JSONUtil;
//import com.unichain.pay.core.PayDirective;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.PayResponse;
//import com.unichain.pay.entity.ChannelDirectiveRecord;
//import com.unichain.pay.service.ChannelDirectiveRecordService;
//import com.unichain.pay.yeepay.YeepayChannelDirecvite;
//import com.unichain.pay.yeepay.YeepayPayRequestHandler;
//import com.unichain.pay.yeepay.domain.BankcardBindResponse;
//import com.unichain.pay.yeepay.domain.BankcardBindVerifyParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//
//@Service("YeepayBankcardBindVerifyDirective")
//public class YeepayBankcardBindVerifyDirective implements PayDirective<YeepayChannelDirecvite, BankcardBindVerifyParam> /*implements PayDirective<BankcardBindParam>*/ {
//
//
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(YeepayChannelDirecvite channelDirective, BankcardBindVerifyParam bankcardBindVerifyParam, PayRequest payRequest) {
//
//        Map data = YeepayPayRequestHandler.build(bankcardBindVerifyParam)
//                .exec(channelDirective);
//        return record(bankcardBindVerifyParam, payRequest, JSONUtil.toJsonStr(data));
//    }
//
//    @Override
//    public BankcardBindVerifyParam buildPayParam(PayRequest payRequest) {
//        BankcardBindVerifyParam bankcardBindVerifyParam = new BankcardBindVerifyParam();
//        YeepayPayRequestHandler.buildPayParam(bankcardBindVerifyParam, payRequest);
//        return bankcardBindVerifyParam;
//    }
//
//    @Override
//    public YeepayChannelDirecvite buildChannelDirective() {
//        return new YeepayChannelDirecvite();
//    }
//
//    @Override
//    public PayResponse record(BankcardBindVerifyParam payParam, PayRequest payRequest, String data) {
//        BankcardBindResponse response = JSONUtil.toBean(data, BankcardBindResponse.class);
//
//        ChannelDirectiveRecord save = new ChannelDirectiveRecord();
//        save.setAccountId(payRequest.getAccountId());
//        save.setAppId(payRequest.getAppId());
//        save.setUserId(payRequest.getUserId());
//        save.setDirectiveCode(getDirectiveCode());
//        save.setCompanyId(payRequest.getCompanyId());
//        save.setPaymentId(payRequest.getPaymentId());
//
//        if ("FAILURE".equals(response.getState())) {
//            save.setReturnCode(response.getErrorcode());
//            save.setReturnMsg(response.getErrormsg());
//        } else if ("SUCCESS".equals(response.getState()) && "TO_VALIDATE".equals(response.getStatus())) {
//            save.setReturnCode(response.getErrorcode());
//            save.setReturnMsg(response.getErrormsg());
//        } else if ("SUCCESS".equals(response.getState()) && "BIND_SUCCESS".equals(response.getStatus())){
//            save.setReturnCode(response.getStatus());
//            save.setReturnMsg("签约成功！");
//        }
//        PayResponse result = new PayResponse();
//
//        channelDirectiveRecordService.save(save);
//        return result;
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "YeepayBankcardBindVerifyDirective";
//    }
//}
