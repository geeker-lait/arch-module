//package com.unichain.pay.sharelink.directive;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.unichain.pay.core.PayDirective;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.PayResponse;
//import com.unichain.pay.entity.ChannelDirectiveRecord;
//import com.unichain.pay.service.ChannelDirectiveRecordService;
//import com.unichain.pay.sharelink.SzsharelinkChannelDirecvite;
//import com.unichain.pay.sharelink.SzsharelinkPayRequestHandler;
//import com.unichain.pay.sharelink.domain.RefundParam;
//import com.unichain.pay.sharelink.domain.RefundResponse;
//import com.unichain.pay.sharelink.utils.ECTXmlUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service("SzsharelinkRefundDirective")
//public class SzsharelinkRefundDirective extends BaseRecord implements PayDirective<SzsharelinkChannelDirecvite, RefundParam> {
//
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(SzsharelinkChannelDirecvite channelDirective, RefundParam payParam, PayRequest payRequest) {
//        String uri = channelDirective.getDirectiveUri();
//        payParam.setMerchantId(channelDirective.getMerchantNo());
//        String data = SzsharelinkPayRequestHandler.build(payParam, channelDirective).exec(uri, ECTXmlUtil.CPREQ_SIREQ);
//
//        return record(payParam, payRequest, data);
//    }
//
//    @Override
//    public RefundParam buildPayParam(PayRequest payRequest) {
//        RefundParam param = new RefundParam();
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
//    public PayResponse record(RefundParam payParam, PayRequest payRequest, String data) {
//        ChannelDirectiveRecord save = super.createChannelDirectiveRecord(payRequest);
//        Map<String, String> resultMap = ECTXmlUtil.xmlToMap(data);
//        RefundResponse response = BeanUtil.mapToBean(resultMap, RefundResponse.class, true);
//        save.setReturnCode(response.getRetFlag());
//        save.setReturnMsg(response.getResultMsg());
//        PayResponse result = new PayResponse();
//        if (!"T".equals(response.getRetFlag())) {
//            // 0默认表示成功，1表示失败
//            save.setState(1);
//        }
//        result.data(response.getResultMsg());
//        channelDirectiveRecordService.save(save);
//        return result;
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "SzsharelinkRefundDirective";
//    }
//}
