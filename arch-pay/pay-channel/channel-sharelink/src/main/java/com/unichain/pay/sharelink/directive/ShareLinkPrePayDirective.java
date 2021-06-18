//package com.unichain.pay.sharelink.directive;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.unichain.pay.core.*;
//import com.unichain.pay.core.util.RedisUtils;
//import com.unichain.pay.entity.ChannelDirectiveRecord;
//import com.unichain.pay.service.ChannelDirectiveRecordService;
//import com.unichain.pay.sharelink.SzsharelinkChannelDirecvite;
//import com.unichain.pay.sharelink.SzsharelinkPayRequestHandler;
//import com.unichain.pay.sharelink.domain.ShareLinkPrePayParam;
//import com.unichain.pay.sharelink.domain.ShareLinkPrePayResponse;
//import com.unichain.pay.sharelink.utils.ECTXmlUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.criteria.*;
//import java.util.*;
//
//@Service("ShareLinkPrePayDirective")
//public class ShareLinkPrePayDirective extends BaseRecord implements PayDirective<SzsharelinkChannelDirecvite, ShareLinkPrePayParam> {
//    @Autowired
//    private ChannelDirectiveRecordService channelDirectiveRecordService;
//
//    @Override
//    public PayResponse exec(SzsharelinkChannelDirecvite channelDirective, ShareLinkPrePayParam payParam, PayRequest payRequest) {
//        payParam.setMerchantId(channelDirective.getMerchantNo());
//        String uri = channelDirective.getDirectiveUri();
//        //  获取protocolId ,根据appId，bankcard，accountId，userId，channelId
//        String channelId = payRequest.getChannelId();
//        ChannelDirectiveRecord param = new ChannelDirectiveRecord();
//        param.setAppId(payRequest.getAppId());
//        //  如果payRequest能拿到bankcard就使用payRequest.getBankcard()
//        //  如果拿不到就根据paymentId去channel_directive_record拿
//        param.setBankcard(payRequest.getBankcard());
//        param.setAccountId(payRequest.getAccountId());
//        param.setUserId(payRequest.getUserId());
//        param.setChannelId(channelId);
//        param.setReturnCode("T");
//        ChannelDirectiveRecord channelDirectiveRecord = getchannelDirectiveRecord(param);
//        if (channelDirectiveRecord != null) {
//            payParam.setProtocolId(channelDirectiveRecord.getProtocolId());
//        }
//
//        String data = SzsharelinkPayRequestHandler
//                .build(payParam, channelDirective)
//                .exec(uri, ECTXmlUtil.CPREQ_QPREQ);
//
//        return record(payParam, payRequest, data);
//    }
//
//    @Override
//    public ShareLinkPrePayParam buildPayParam(PayRequest payRequest) {
//        ShareLinkPrePayParam shareLinkPrePayParam = new ShareLinkPrePayParam();
//        SzsharelinkPayRequestHandler.buildPayParam(shareLinkPrePayParam, payRequest);
//        return shareLinkPrePayParam;
//    }
//
//    @Override
//    public SzsharelinkChannelDirecvite buildChannelDirective() {
//        return new SzsharelinkChannelDirecvite();
//    }
//
//    @Override
//    public String getDirectiveCode() {
//        return "ShareLinkPrePayDirective";
//    }
//
//    @Override
//    public PayResponse record(ShareLinkPrePayParam payParam, PayRequest payRequest, String data) {
//        Map<String, String> stringStringMap = ECTXmlUtil.xmlToMap(data);
//        ShareLinkPrePayResponse response = BeanUtil.mapToBean(stringStringMap, ShareLinkPrePayResponse.class, true);
//
//        ChannelDirectiveRecord save = super.createChannelDirectiveRecord(payRequest);
//        save.setBankcard(payParam.getAccount());
//        save.setChannelId(payRequest.getChannelId());
//        save.setDirectiveCode(getDirectiveCode());
//        save.setReturnCode(response.getRetFlag());
//        save.setReturnMsg(response.getResultMsg());
//        save.setPaymentId(payRequest.getPaymentId());
//        // 记录金额
//        save.setAmount(payParam.getAmount());
//        save.setBizCode(BizCode.PAY.val());
//
//        PayResponse result = new PayResponse();
//
//        PayResult payResult = new PayResult();
//        //  retFlag(T-成功，F-失败，P-未明) resultCode(0032表示支付成功)
//        if ("T".equalsIgnoreCase(response.getRetFlag()) && "0032".equals(response.getResultCode())) {
//
//            payResult.setStatus(PayResultStatusEnum.SUCCESS);
//            payResult.setPaymentId(payRequest.getPaymentId());
//            payResult.setNeedSms(3);
//        } else if ("F".equalsIgnoreCase(response.getRetFlag()) && ("0099".equals(response.getResultCode()) || "9998".equals(response.getResultCode()))) {
//            //  交易结果为0099或9998时返回。此时交易并未结束，商户需要调用4.5协议支付短信验证接口。
//            String key = payRequest.getAccountId() + "_shareLink_prepay_smsSendNo";
//            RedisUtils.set(key, response.getSmsSendNo(), 10 * 60);
//
//            payResult.setStatus(PayResultStatusEnum.SUCCESS);
//            payResult.setPaymentId(payRequest.getPaymentId());
//            //  0：需要接收短信验证码，且需要调用快捷协议支付确认支付接口；
//            //  1：不需要接收短信验证码，且需要调用快捷协议支付确认支付接口；
//            //  3：不需要调用快捷协议支付确认支付接口；
//            payResult.setNeedSms(0);
//        } else {
//            // 0默认表示成功，1表示失败
//            save.setState(1);
//            payResult.setStatus(PayResultStatusEnum.FAIL);
//        }
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
//    public Integer getResultCode(ShareLinkPrePayResponse response) {
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
