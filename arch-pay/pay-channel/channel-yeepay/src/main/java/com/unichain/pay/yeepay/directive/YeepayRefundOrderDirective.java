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
//import com.unichain.pay.yeepay.domain.RefundOrderParam;
//import com.unichain.pay.yeepay.domain.RefundOrderResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
///**
// * @Author lait.zhang@gmail.com
// * @Tel 15801818092
// * @Date 12/4/2019
// * @Description ${Description}
// */
//@Service("YeepayRefundOrderDirective")
//public class YeepayRefundOrderDirective implements PayDirective<YeepayChannelDirecvite, RefundOrderParam> {
//
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(YeepayChannelDirecvite channelDirective, RefundOrderParam payParam, PayRequest payRequest) {
//
//        // 响应结果
//        Map data = YeepayPayRequestHandler.build(payParam)
//                .exec(channelDirective);
//        record(payParam, payRequest,  JSONUtil.toJsonStr(data));
//        PayResponse payResponse = new PayResponse();
//        return payResponse.data(data);
//    }
//
//    @Override
//    public RefundOrderParam buildPayParam(PayRequest payRequest) {
//        RefundOrderParam refundOrderParam = new RefundOrderParam();
//        YeepayPayRequestHandler.buildPayParam(refundOrderParam, payRequest);
//        return refundOrderParam;
//    }
//
//    @Override
//    public YeepayChannelDirecvite buildChannelDirective() {
//        return new YeepayChannelDirecvite();
//    }
//
//    @Override
//    public PayResponse record(RefundOrderParam payParam, PayRequest payRequest, String data) {
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
//        return "YeepayRefundOrderDirective";
//    }
//}
