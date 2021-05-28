//package com.unichain.pay.sharelink.directive;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.unichain.pay.core.*;
//import com.unichain.pay.core.util.RedisUtils;
//import com.unichain.pay.entity.ChannelDirectiveRecord;
//import com.unichain.pay.service.ChannelDirectiveRecordService;
//import com.unichain.pay.sharelink.SzsharelinkChannelDirecvite;
//import com.unichain.pay.sharelink.SzsharelinkPayRequestHandler;
//import com.unichain.pay.sharelink.domain.ShareLinkPayParam;
//import com.unichain.pay.sharelink.domain.ShareLinkPayResponse;
//import com.unichain.pay.sharelink.utils.ECTXmlUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.NoSuchElementException;
//
//@Service("ShareLinkPayDirective")
//public class ShareLinkPayDirective implements PayDirective<SzsharelinkChannelDirecvite, ShareLinkPayParam> {
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(SzsharelinkChannelDirecvite channelDirective, ShareLinkPayParam payParam, PayRequest payRequest) {
//        payParam.setMerchantId(channelDirective.getMerchantNo());
//        String uri = channelDirective.getDirectiveUri();
//
//        //  获取protocolId ,根据appId，bankcard，accountId，userId，channelId
//        String channelId = payRequest.getChannelId();
//        ChannelDirectiveRecord param = new ChannelDirectiveRecord();
//        param.setAppId(payRequest.getAppId());
//        param.setBankcard(payRequest.getBankcard());
//        param.setAccountId(payRequest.getAccountId());
//        param.setUserId(payRequest.getUserId());
//        param.setChannelId(channelId);
//        param.setReturnCode("T");
//        ChannelDirectiveRecord channelDirectiveRecord = getchannelDirectiveRecord(param);
//        if (channelDirectiveRecord != null) {
//            payParam.setProtocolId(channelDirectiveRecord.getProtocolId());
//        }
//        //  短信验证码编码
//        String smsSendNo = RedisUtils.getStr(payRequest.getAccountId() + "_shareLink_prepay_smsSendNo");
//        smsSendNo = smsSendNo != null ? smsSendNo.replace("\"", "") : null;
//        payParam.setSmsSendNo(smsSendNo);
//        String data = SzsharelinkPayRequestHandler
//                .build(payParam, channelDirective)
//                .exec(uri, ECTXmlUtil.CPREQ_QPREQ);
//
//        return record(payParam, payRequest, data);
//    }
//
//    @Override
//    public ShareLinkPayParam buildPayParam(PayRequest payRequest) {
//        ShareLinkPayParam shareLinkPayParam = new ShareLinkPayParam();
//        SzsharelinkPayRequestHandler.buildPayParam(shareLinkPayParam, payRequest);
//        return shareLinkPayParam;
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
//    public PayResponse record(ShareLinkPayParam payParam, PayRequest payRequest, String data) {
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
//        save.setReturnCode(response.getRetFlag());
//        save.setReturnMsg(response.getResultMsg());
//        save.setBizCode(BizCode.PAY.val());
//        save.setAmount(payParam.getAmount());
//        PayResponse result = new PayResponse();
//
//        PayResult payResult = new PayResult();
//        //  retFlag(T-成功，F-失败，P-未明) resultCode(0032表示支付成功)
//        //  needSms：0，1，3
//        //  0：需要接收短信验证码，且需要调用快捷协议支付确认支付接口；
//        //  1：不需要接收短信验证码，且需要调用快捷协议支付确认支付接口；
//        //  3：不需要调用快捷协议支付确认支付接口；
//        if ("T".equalsIgnoreCase(response.getRetFlag()) && "0032".equals(response.getResultCode())) {
//
//            payResult.setStatus(PayResultStatusEnum.SUCCESS);
//            payResult.setPaymentId(payParam.getPayOrderId());
//            payResult.setNeedSms(3);
//        } else if ("F".equalsIgnoreCase(response.getRetFlag()) && ("0099".equals(response.getResultCode()) || "9998".equals(response.getResultCode()))) {
//            //  交易结果为0099或9998时返回。此时交易并未结束，商户需要调用4.5协议支付短信验证接口。建行通道端会再次发短信。
//            String key = payRequest.getAccountId() + "_shareLink_prepay_smsSendNo";
//            RedisUtils.set(key, response.getSmsSendNo(), 10 * 60);
//
//            payResult.setStatus(PayResultStatusEnum.SUCCESS);
//            payResult.setPaymentId(payParam.getPayOrderId());
//            payResult.setNeedSms(0);
//        } else {
//            // 0默认表示成功，1表示失败
//            save.setState(1);
//            payResult.setStatus(PayResultStatusEnum.FAIL);
//        }
//
//        Integer code = getResultCode(response);
//        payResult.setCode(code);
//        payResult.setResultMsg(response.getResultMsg());
//        result.data(payResult);
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
//        Specification<ChannelDirectiveRecord> specification = new Specification<ChannelDirectiveRecord>() {
//            @Override
//            public Predicate toPredicate(Root<ChannelDirectiveRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//
//                List<Predicate> list = new ArrayList<>();
//                list.add(criteriaBuilder.isNotNull(root.get("protocolId")));
//                list.add(criteriaBuilder.equal(root.get("appId"), channelDirectiveRecord.getAppId()));
//                list.add(criteriaBuilder.equal(root.get("bankcard"), channelDirectiveRecord.getBankcard()));
//                list.add(criteriaBuilder.equal(root.get("accountId"), channelDirectiveRecord.getAccountId()));
//                list.add(criteriaBuilder.equal(root.get("userId"), channelDirectiveRecord.getUserId()));
//                list.add(criteriaBuilder.equal(root.get("returnCode"), channelDirectiveRecord.getReturnCode()));
//                list.add(criteriaBuilder.equal(root.get("appId"), channelDirectiveRecord.getAppId()));
//
//                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
//            }
//        };
//        ChannelDirectiveRecord channelDirectiveRecord1 = null;
//        try {
//            channelDirectiveRecord1 = channelDirectiveRecordService.getCrudRepository().findOne(specification).get();
//        } catch (NoSuchElementException e) {
//            e.printStackTrace();
//        }
//
//        return channelDirectiveRecord1;
//    }
//
//    public Integer getResultCode(ShareLinkPayResponse response) {
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
