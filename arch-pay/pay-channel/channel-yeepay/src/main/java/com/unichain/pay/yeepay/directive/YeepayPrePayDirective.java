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
//import com.unichain.pay.yeepay.domain.YeepayPayResponse;
//import com.unichain.pay.yeepay.domain.YeepayPrePayParam;
//import com.unichain.pay.yeepay.domain.YeepayPrePayResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service("YeepayPrePayDirective")
//public class YeepayPrePayDirective implements PayDirective<YeepayChannelDirecvite, YeepayPrePayParam> {
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(YeepayChannelDirecvite channelDirective, YeepayPrePayParam bankcardPrePayParam, PayRequest payRequest) {
//        bankcardPrePayParam.setMerchantno(channelDirective.getMerchantNo());
//
//        Map data = YeepayPayRequestHandler.build(bankcardPrePayParam)
//                .exec(channelDirective);
//        System.out.println("responseData:" + data.toString());
//        return record(bankcardPrePayParam, payRequest,  JSONUtil.toJsonStr(data));
//
//    }
//
//    @Override
//    public YeepayPrePayParam buildPayParam(PayRequest payRequest) {
//        YeepayPrePayParam bankcardPrePayParam = new YeepayPrePayParam();
//        YeepayPayRequestHandler.buildPayParam(bankcardPrePayParam, payRequest);
//        return bankcardPrePayParam;
//    }
//
//    @Override
//    public YeepayChannelDirecvite buildChannelDirective() {
//        return new YeepayChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "YeepayPrePayDirective";
//    }
//
//    @Override
//    public PayResponse record(YeepayPrePayParam bankcardPrePayParam, PayRequest payRequest, String data) {
//        // 保存记录
//        ChannelDirectiveRecord channelDirectiveRecord = new ChannelDirectiveRecord();
//
//        channelDirectiveRecord.setPaymentId(payRequest.getPaymentId());
//
//        YeepayPrePayResponse bankcardPrePayResponse = JSONObject.parseObject(data, YeepayPrePayResponse.class);
//        String returnCode = bankcardPrePayResponse.getStatus();
//        channelDirectiveRecord.setReturnCode(returnCode);
//        if ("FAILURE".equals(bankcardPrePayResponse.getState())) {
//            channelDirectiveRecord.setReturnCode(bankcardPrePayResponse.getErrorcode());
//            channelDirectiveRecord.setReturnMsg(bankcardPrePayResponse.getErrormsg());
//        }
//
//
//        PayResponse result = new PayResponse();
//
//        if (!bankcardPrePayResponse.getState().equals("SUCCESS")) {
//            // 0默认表示成功，1表示失败
//            channelDirectiveRecord.setState(1);
//        } else {
//            result.data(payRequest.getPaymentId());
//        }
//        channelDirectiveRecordService.save(channelDirectiveRecord);
//        return result;
//    }
//}
