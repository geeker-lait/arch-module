//package com.unichain.pay.sharelink.directive;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.unichain.pay.core.PayDirective;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.PayResponse;
//import com.unichain.pay.core.util.RedisUtils;
//import com.unichain.pay.entity.ChannelDirectiveRecord;
//import com.unichain.pay.service.ChannelDirectiveRecordService;
//import com.unichain.pay.sharelink.SzsharelinkChannelDirecvite;
//import com.unichain.pay.sharelink.SzsharelinkPayRequestHandler;
//import com.unichain.pay.sharelink.domain.PayForAnotherParam;
//import com.unichain.pay.sharelink.domain.ShareLinkPayParam;
//import com.unichain.pay.sharelink.domain.ShareLinkPayResponse;
//import com.unichain.pay.sharelink.utils.ECTXmlUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by xiaoyao on 2019/12/13 2:46 PM
// */
//@Service("ShareLinkPayForAnotherDirective")
//public class ShareLinkPayForAnotherDirective  implements PayDirective<SzsharelinkChannelDirecvite, PayForAnotherParam> {
//
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(SzsharelinkChannelDirecvite channelDirective, PayForAnotherParam payParam, PayRequest payRequest) {
//        payParam.setMerchantId(channelDirective.getMerchantNo());
//        String uri = channelDirective.getDirectiveUri();
//
//        String data = SzsharelinkPayRequestHandler
//                .build(payParam, channelDirective)
//                .exec(uri, ECTXmlUtil.CPREQ_SPREQ);
//
//        return record(payParam, payRequest, data);
//    }
//
//    @Override
//    public PayForAnotherParam buildPayParam(PayRequest payRequest) {
//        PayForAnotherParam payForAnotherParam = new PayForAnotherParam();
//        SzsharelinkPayRequestHandler.buildPayParam(payForAnotherParam, payRequest);
//        return payForAnotherParam;
//    }
//
//    @Override
//    public SzsharelinkChannelDirecvite buildChannelDirective() {
//        return new SzsharelinkChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "ShareLinkPayDirective";
//    }
//
//    @Override
//    public PayResponse record(PayForAnotherParam payParam, PayRequest payRequest, String data) {
//        Map<String, String> stringStringMap = ECTXmlUtil.xmlToMap(data);
//        ShareLinkPayResponse response = BeanUtil.mapToBean(stringStringMap, ShareLinkPayResponse.class, true);
//
//        ChannelDirectiveRecord save = new ChannelDirectiveRecord();
//        save.setAccountId(payRequest.getAccountId());
//        save.setAppId(payRequest.getAppId());
//        save.setUserId(payRequest.getUserId());
//        save.setChannelId(payRequest.getChannelId());
//        save.setDirectiveCode(getDirectiveCode());
//        save.setCompanyId(payRequest.getCompanyId());
//        //save.setPhone(payParam.getMobile());
//
//        save.setReturnCode(response.getRetFlag());
//        save.setReturnMsg(response.getResultMsg());
//        PayResponse result = new PayResponse();
//        if (!"T".equals(response.getRetFlag())) {
//            // 0默认表示成功，1表示失败
//            save.setState(1);
//        } else {
//            result.data(response.getResultMsg());
//        }
//        channelDirectiveRecordService.save(save);
//        return result;
//    }
//
//    /**
//     * 获取ChannelDirectiveRecord，根据（appId，bankcard，accountId，userId，channelId）
//     *
//     * @param channelDirectiveRecord
//     * @return
//     */
//    private ChannelDirectiveRecord getchannelDirectiveRecord(ChannelDirectiveRecord channelDirectiveRecord) {
//        List<ChannelDirectiveRecord> channelDirectiveRecords = channelDirectiveRecordService.find(channelDirectiveRecord);
//        if (!CollectionUtils.isEmpty(channelDirectiveRecords)) {
//            return channelDirectiveRecords.get(0);
//        }
//        return null;
//    }
//}
