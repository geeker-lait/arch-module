//package com.unichain.pay.sharelink;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.unichain.pay.core.ChannelDirective;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.PayRequestHandler;
//import com.unichain.pay.sharelink.utils.ECTXmlUtil;
//import com.unichain.pay.sharelink.utils.HttpClientUtil;
//import com.unichain.pay.sharelink.utils.MerchantSignAndVerify;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Map;
//
//public class SzsharelinkPayRequestHandler implements PayRequestHandler {
//    // 日志
//    private static final Logger logger = LoggerFactory.getLogger(SzsharelinkPayRequestHandler.class);
//
//    private PayParam payParam;
//    private Map<String, Object> mapParam;
//    //私钥解密密钥
//    private String secretKey;
//    //参数加密密钥
//    private String encryptKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCme526ar35LeqRQaWzFvAd2NTKPmHdNfizeubSO3";
//
//
//    public static SzsharelinkPayRequestHandler build(PayParam payParam, ChannelDirective channelDirective) {
//        SzsharelinkPayRequestHandler szsharelinkPayRequestHandler = new SzsharelinkPayRequestHandler();
//        szsharelinkPayRequestHandler.payParam = payParam;
//        szsharelinkPayRequestHandler.secretKey = channelDirective.getSecretKey();
//
//        // 参数加密处理
//        payParam.encrypt(szsharelinkPayRequestHandler.encryptKey);
//
//        szsharelinkPayRequestHandler
//                .sign(channelDirective.getPrivateKey())
//                /*.encrypt(channelDirective.getMerchantNo(), channelDirective.getSecretKey())*/;
//        // 转换标准参数
//        szsharelinkPayRequestHandler.mapParam = BeanUtil.beanToMap(payParam, false, true);
//        return szsharelinkPayRequestHandler;
//    }
//
//    /**
//     * 构建支付参数
//     *
//     * @param payParam
//     * @param payRequest
//     * @param <PP>
//     */
//    public static <PP extends PayParam> void buildPayParam(PP payParam, PayRequest payRequest) {
//
//        payParam.convert(payRequest.getPayParamMap(), payRequest);
//    }
//
//    @Override
//    public SzsharelinkPayRequestHandler sign(String prikeyvalue) {
//        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(payParam);
//        String linkString = MerchantSignAndVerify.createLinkString(stringObjectMap);
//        String sign = new String(MerchantSignAndVerify.sign(linkString, prikeyvalue, secretKey));
//        payParam.setSign(sign);
//        return this;
//    }
//
//    @Override
//    public SzsharelinkPayRequestHandler encrypt(String oid_partner, String YT_RSA_PUBLIC) {
//        return this;
//    }
//
//    @Override
//    public String toJsonParam() {
//        return null;
//    }
//
//    public String exec(String uri, String CSReqId) {
//        String s = ECTXmlUtil.mapToXml(this.mapParam, CSReqId);
//        String responseString = null;
//        try {
//            responseString = HttpClientUtil.postToServerByXml(s, uri);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return responseString;
//    }
//}
