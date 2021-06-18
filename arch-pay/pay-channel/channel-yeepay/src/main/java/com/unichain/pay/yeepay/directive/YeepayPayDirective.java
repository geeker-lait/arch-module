//package com.unichain.pay.yeepay.directive;
//
//import cn.hutool.json.JSONUtil;
//import com.alibaba.fastjson.JSONObject;
//import com.unichain.pay.core.PayDirective;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.PayResponse;
//import com.unichain.pay.entity.ChannelDirectiveRecord;
//import com.unichain.pay.service.ChannelDirectiveRecordService;
//import com.unichain.pay.yeepay.YeepayChannelDirecvite;
//import com.unichain.pay.yeepay.YeepayPayRequestHandler;
//import com.unichain.pay.yeepay.domain.YeepayPayParam;
//import com.unichain.pay.yeepay.domain.YeepayPayResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service("YeepayPayDirective")
//public class YeepayPayDirective implements PayDirective<YeepayChannelDirecvite, YeepayPayParam> {
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(YeepayChannelDirecvite channelDirective, YeepayPayParam bankcardPayParam, PayRequest payRequest) {
//
//        Map data = YeepayPayRequestHandler.build(bankcardPayParam)
//                .exec(channelDirective);
//
//        return record(bankcardPayParam, payRequest, JSONUtil.toJsonStr(data));
//
//    }
//
//    @Override
//    public YeepayPayParam buildPayParam(PayRequest payRequest) {
//        YeepayPayParam bankcardPayParam = new YeepayPayParam();
//        YeepayPayRequestHandler.buildPayParam(bankcardPayParam, payRequest);
//        return bankcardPayParam;
//    }
//
//    @Override
//    public YeepayChannelDirecvite buildChannelDirective() {
//        return new YeepayChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "YeepayPayDirective";
//    }
//
//    @Override
//    public PayResponse record(YeepayPayParam bankcardPayParam, PayRequest payRequest, String data) {
//        // 保存记录
//        ChannelDirectiveRecord channelDirectiveRecord = new ChannelDirectiveRecord();
//
//        channelDirectiveRecord.setAccountId(payRequest.getAccountId());
//        channelDirectiveRecord.setAppId(payRequest.getAppId());
//        channelDirectiveRecord.setChannelId(payRequest.getChannelId());
//        channelDirectiveRecord.setUserId(payRequest.getUserId());
//        channelDirectiveRecord.setCompanyId(payRequest.getCompanyId());
//        channelDirectiveRecord.setDirectiveCode(getDirectiveCode());
//        //channelDirectiveRecord.setBankcard(Base64.decode(bankcardPayParam.getBankCardNo()));
//
//        YeepayPayResponse bankcardPrePayResponse = JSONObject.parseObject(data, YeepayPayResponse.class);
//        String returnCode = bankcardPrePayResponse.getState();
//        channelDirectiveRecord.setReturnCode(returnCode);
//        channelDirectiveRecord.setReturnMsg("支付成功，状态:"+bankcardPrePayResponse.getStatus());
//        channelDirectiveRecord.setPaymentId(payRequest.getPaymentId());
//
//        PayResponse result = new PayResponse();
//
//        if (!returnCode.equals("SUCCESS")) {
//            // 0默认表示成功，1表示失败
//            channelDirectiveRecord.setState(1);
//        } else {
//            result.data(payRequest.getPaymentId());
//        }
//        channelDirectiveRecordService.save(channelDirectiveRecord);
//        return result;
//    }
//
//
//}
