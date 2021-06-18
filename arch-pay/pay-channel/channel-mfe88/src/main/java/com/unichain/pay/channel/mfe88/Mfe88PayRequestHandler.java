//package com.unichain.pay.channel.mfe88;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.json.JSONUtil;
//import com.unichain.pay.channel.mfe88.demo.entity.Trade;
//import com.unichain.pay.channel.mfe88.utils.HttpClientUtil;
//import com.unichain.pay.channel.mfe88.utils.PropUtils;
//import com.unichain.pay.channel.mfe88.utils.TraderRSAUtil;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//import org.arch.payment.sdk.PayParam;
//import org.arch.payment.sdk.PayRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.net.URLEncoder;
//import java.util.Map;
//
//import static com.unichain.pay.channel.mfe88.demo.Demo.rsasign;
//import static com.unichain.pay.channel.mfe88.demo.UppDemo.md5UpperCase;
//
//public class Mfe88PayRequestHandler implements PayRequestHandler {
//
//    // 日志
//    private static final Logger logger = LoggerFactory.getLogger(Mfe88PayRequestHandler.class);
//
//    private PayParam payParam;
//    private Map<String, Object> mapParam;
//
//
//    private String secretKey;
//
//
//    private Mfe88PayRequestHandler() {
//
//    }
//
//    public static Mfe88PayRequestHandler build(PayParam payParam, ChannelDirective channelDirective) {
//        Mfe88PayRequestHandler mfe88PayRequestHandler = new Mfe88PayRequestHandler();
//        mfe88PayRequestHandler.payParam = payParam;
//        mfe88PayRequestHandler.secretKey = channelDirective.getSecretKey();
//
//        // 参数加密处理
//        payParam.encrypt(channelDirective.getSecretKey());
//
//        // 转换标准参数
//        mfe88PayRequestHandler.mapParam = BeanUtil.beanToMap(payParam, false, true);
//
//        mfe88PayRequestHandler
//                .sign(channelDirective.getPrivateKey())
//                .encrypt(channelDirective.getMerchantNo(), channelDirective.getSecretKey());
//        // 转换标准参数
//        mfe88PayRequestHandler.mapParam = BeanUtil.beanToMap(payParam, false, true);
//
//        return mfe88PayRequestHandler;
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
//    public Mfe88PayRequestHandler sign(String privateKey) {
//        Trade trade = new Trade();
//        BeanUtil.copyProperties(this.payParam, trade);
//        trade.setMerchantPrivateKey(privateKey);
//        trade.setKeyPassword(this.secretKey);
//        trade.setKeyType("RSACert");
//        //String s = Demo.createLinkString(BeanUtil.beanToMap(payParam));
//        String s = PropUtils.beanToAsciiSortString1(this.payParam);
//        try {
//            this.payParam.setSign(generateSign(trade, s));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return this;
//    }
//
//    /**
//     * @param oid_partner   商户号
//     * @param YT_RSA_PUBLIC rsa公钥
//     * @return
//     */
//    @Override
//    public Mfe88PayRequestHandler encrypt(String oid_partner, String YT_RSA_PUBLIC) {
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
//    private static String generateSign(Trade bean, String content) throws Exception {
//        String sign = "";
//        if ("2".equals(bean.getSignType())) {
//            try {
//                System.out.println("rsa签串:" + content);
//                sign = rsasign(content, bean.getPrivateKey());
//                sign = URLEncoder.encode(sign, "UTF-8");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if ("3".equals(bean.getSignType())) {
//            System.out.println("RSACert签串:" + content);
//            sign = TraderRSAUtil.getInstance().sign(content, bean.getMerchantPrivateKey().trim(), bean.getKeyPassword().trim());// 商户签名，商户私钥
////            sign = TraderRSAUtil.getInstance().sign(content, , bean.getKeyPassword().trim());// 商户签名，商户私钥
//            System.out.println("RSACert sign:" + sign);
//        } else {
//            sign = md5UpperCase(content + bean.getMd5Key(), "UTF-8");// 商户签名数据
//            System.out.println("md5签串:" + content + bean.getMd5Key());
//        }
//        return sign;
//    }
//
//    public String exec(String uri) {
//        String body = null;
//        logger.info("请求参数 >>>> {}", JSONUtil.toJsonStr(mapParam));
//        try {
//            body = HttpClientUtil.post(uri, mapParam);
//            logger.info("请求返回 >>> {}", body);
//        } catch (Exception e) {
//            body = e.getMessage();
//        }
////        HttpRequest request = new HttpRequest(uri);
////        request.setMethod(Method.POST);
////        request.form(this.jsonParam);
////        cn.hutool.http.HttpResponse execute = request.execute();
////        String body = execute.body();
////        System.out.println(body);
//        return body;
//    }
//
//    private String doPost(String url, String jsonStr, String charset) {
//        HttpClient httpClient = null;
//        HttpPost httpPost = null;
//        String result = null;
//        try {
//            httpClient = HttpClientBuilder.create().build();// new DefaultHttpClient();
//            httpPost = new HttpPost(url);
//            System.out.println(jsonStr);
//            // 设置参数
//            StringEntity s = new StringEntity(jsonStr);
//            s.setContentEncoding("UTF-8");
//            s.setContentType("application/json");
//            httpPost.setEntity(s);
//            HttpResponse response = httpClient.execute(httpPost);
//            if (response != null) {
//                HttpEntity resEntity = response.getEntity();
//                if (resEntity != null) {
//                    result = EntityUtils.toString(resEntity, charset);
//                }
//                System.out.println("response body:" + result);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result;
//    }
//}
