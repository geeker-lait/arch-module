//package com.unichain.pay.channel.lianlian;
//
//import com.alibaba.fastjson.JSONObject;
//import com.lianpay.share.security.LianLianPaySecurity;
//import com.unichain.pay.channel.lianlian.util.GenSignUtils;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.PayRequestHandler;
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//
//import java.lang.reflect.InvocationTargetException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//public class LianlianPayRequestHandler implements PayRequestHandler {
//
//    private PayParam payParam;
//    private JSONObject jsonParam;
//
//    private LianlianPayRequestHandler() {
//
//    }
//
//    public static LianlianPayRequestHandler build(PayParam payParam) {
//        LianlianPayRequestHandler lianlianPayRequestHandler = new LianlianPayRequestHandler();
//        lianlianPayRequestHandler.setPayParam(payParam);
//        return lianlianPayRequestHandler;
//    }
//
//    /**
//     * 生成待签名串
//     */
//    public static String genSignData(JSONObject jsonObject) {
//        StringBuffer content = new StringBuffer();
//        List<String> keys = new ArrayList<String>(jsonObject.keySet());
//        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
//        for (int i = 0; i < keys.size(); i++) {
//            String key = keys.get(i);
//            if ("sign".equals(key)) {
//                continue;
//            }
//            String value = jsonObject.getString(key);
//            if (StringUtils.isBlank(value)) {
//                continue;
//            }
//            content.append((i == 0 ? "" : "&") + key + "=" + value);
//        }
//        String signSrc = content.toString();
//        if (signSrc.startsWith("&")) {
//            signSrc = signSrc.replaceFirst("&", "");
//        }
//        return signSrc;
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
//        try {
//            BeanUtils.populate(payParam, payRequest.getPayParamMap());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public LianlianPayRequestHandler sign(String prikeyvalue) {
//        try {
//            this.payParam.setSign(GenSignUtils.genSign(JSONObject.parseObject(JSONObject.toJSONString(payParam)), "", prikeyvalue));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return this;
//    }
//
//    @Override
//    public LianlianPayRequestHandler encrypt(String oid_partner, String YT_RSA_PUBLIC) {
//        jsonParam = new JSONObject();
//        try {
//            String encryptStr = LianLianPaySecurity.encrypt(JSONObject.toJSONString(payParam), YT_RSA_PUBLIC);
//            jsonParam.put("oid_partner", oid_partner);
//            jsonParam.put("pay_load", encryptStr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return this;
//    }
//
//    @Override
//    public String toJsonParam() {
//        return jsonParam.toJSONString();
//    }
//
//
//    private void setPayParam(PayParam payParam) {
//        this.payParam = payParam;
//    }
//
//
//    /**
//     * "https://test.lianlianpay-inc.com/mpayapi/v1/bankcardprepay"
//     *
//     * @param uri
//     * @return
//     */
//    public String exec(String uri) {
//        String res = doPost(uri, toJsonParam(), "UTF-8");
//        //JSONObject jsonRs = JSONObject.parseObject(res);
//        //boolean checkSign = SHA256withRSAUtil.getInstance().checksign(public_key, genSignData(jsonRs), jsonRs.getString("sign"));
//        //System.out.println("签名验证结果为:"+checkSign);
//        return res;
//    }
//
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
//
//
//    public String getTimeStamp() {
//        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date();
//        return df.format(date);
//    }
//
//}
