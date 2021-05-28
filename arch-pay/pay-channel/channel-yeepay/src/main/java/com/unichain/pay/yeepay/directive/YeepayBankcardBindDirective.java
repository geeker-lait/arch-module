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
//import com.unichain.pay.yeepay.domain.BankcardBindParam;
//import com.unichain.pay.yeepay.domain.BankcardBindResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//
//@Service("YeepayBankcardBindDirective")
//public class YeepayBankcardBindDirective implements PayDirective<YeepayChannelDirecvite, BankcardBindParam> {
//
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(YeepayChannelDirecvite channelDirective, BankcardBindParam bankcardBindParam, PayRequest payRequest) {
//
//        // 响应结果
//        Map data = YeepayPayRequestHandler.build(bankcardBindParam)
//                .exec(channelDirective);
//
//        record(bankcardBindParam, payRequest, JSONUtil.toJsonStr(data));
//        PayResponse payResponse = new PayResponse();
//        return payResponse.data(data);
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
//    public PayResponse record(BankcardBindParam payParam, PayRequest payRequest, String data) {
//        BankcardBindResponse response = JSONUtil.toBean(data, BankcardBindResponse.class);
//
//        ChannelDirectiveRecord save = new ChannelDirectiveRecord();
//        save.setAccountId(payRequest.getAccountId());
//        save.setAppId(payRequest.getAppId());
//        save.setUserId(payRequest.getUserId());
//
//        save.setDirectiveCode(getDirectiveCode());
//        save.setCompanyId(payRequest.getCompanyId());
//        save.setPhone(payParam.getPhone());
//        // 订单号
//        save.setPaymentId(payRequest.getPaymentId());
//        if ("FAILURE".equals(response.getState())) {
//            save.setReturnCode(response.getErrorcode());
//            save.setReturnMsg(response.getErrormsg());
//        }
//        channelDirectiveRecordService.save(save);
//        return null;
//    }
//
//    @Override
//    public BankcardBindParam buildPayParam(PayRequest payRequest) {
//        BankcardBindParam bankcardBindParam = new BankcardBindParam();
//        YeepayPayRequestHandler.buildPayParam(bankcardBindParam, payRequest);
//        return bankcardBindParam;
//    }
//
//    @Override
//    public YeepayChannelDirecvite buildChannelDirective() {
//        return new YeepayChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "YeepayBankcardBindDirective";
//    }
//}
