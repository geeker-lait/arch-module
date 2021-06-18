//package com.unichain.pay.yeepay;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.json.JSONUtil;
//import com.unichain.pay.core.ChannelDirective;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.PayRequestHandler;
//import com.unichain.pay.yeepay.service.YeepayService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.Map;
//
//public class YeepayPayRequestHandler implements PayRequestHandler {
//
//    // 日志
//    private static final Logger logger = LoggerFactory.getLogger(YeepayPayRequestHandler.class);
//
//    private PayParam payParam;
//    private Map<String, Object> mapParam;
//
//
//    private String secretKey;
//
//
//    private YeepayPayRequestHandler() {
//
//    }
//
//    public static YeepayPayRequestHandler build(PayParam payParam) {
//        YeepayPayRequestHandler yeepayPayRequestHandler = new YeepayPayRequestHandler();
//
//        // 转换标准参数
//        yeepayPayRequestHandler.mapParam = BeanUtil.beanToMap(payParam, false, true);
//
//        return yeepayPayRequestHandler;
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
//        payParam.convert(payRequest.getPayParamMap(), payRequest);
//    }
//
//
//    @Override
//    public YeepayPayRequestHandler sign(String privateKey) {
//
//        return this;
//    }
//
//    /**
//     * @param oid_partner   商户号
//     * @param YT_RSA_PUBLIC rsa公钥
//     * @return
//     */
//    @Override
//    public YeepayPayRequestHandler encrypt(String oid_partner, String YT_RSA_PUBLIC) {
//
//        return this;
//    }
//
//    @Override
//    public String toJsonParam() {
//        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(payParam, false, true);
//        return JSONUtil.toJsonStr(stringObjectMap);
//    }
//
//
//    public Map exec(ChannelDirective channelDirective) {
//        logger.info("请求参数 >>>> {}", JSONUtil.toJsonStr(mapParam));
//        try {
//            return YeepayService.yeepayYOP1(mapParam, channelDirective.getDirectiveUri(), channelDirective.getMerchantNo(), channelDirective.getPrivateKey());
//        } catch (IOException e) {
//            logger.error("YeepayPayRequestHandler>>>exec异常信息{}，请求参数 >>>> {}", e.toString(), JSONUtil.toJsonStr(mapParam));
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//}
