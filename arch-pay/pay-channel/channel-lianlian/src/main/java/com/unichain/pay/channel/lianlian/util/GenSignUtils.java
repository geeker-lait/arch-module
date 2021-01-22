package com.unichain.pay.channel.lianlian.util;

import com.alibaba.fastjson.JSONObject;
import com.lianpay.share.security.MACCoder;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author liuwm@yintong.com.cn
 * @description
 * @date 2018/11/13 10:01
 */
public class GenSignUtils {

    public static String genSign(JSONObject reqObj, String TRADER_MD5_KEY, String prikeyvalue) {
        String sign = reqObj.getString("sign");
        String sign_type = reqObj.getString("sign_type");
        // // 生成待签名串
        String sign_src = genSignData(reqObj);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]待签名原串" + sign_src);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]签名串" + sign);
        System.out.println("加key后的串:" + sign_src);

        if ("HMAC".equals(sign_type)) {
            sign_src += "&key=" + TRADER_MD5_KEY;
            try {
                return MACCoder.encodeHmacSHA256(sign_src.getBytes(StandardCharsets.UTF_8), TRADER_MD5_KEY.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if ("SHA256withRSA".equals(sign_type)) {
            return SHA256withRSAUtil.sign(prikeyvalue, sign_src);
        }
        return "";
    }

    /**
     * 生成待签名串
     *
     * @param jsonObject
     * @return
     */
    public static String genSignData(JSONObject jsonObject) {
        StringBuffer content = new StringBuffer();

        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(jsonObject.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            // sign 和ip_client 不参与签名
            if ("sign".equals(key)) {
                continue;
            }
            String value = jsonObject.getString(key);
            // 空串不参与签名
            if ((StringUtils.isBlank(value))) {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&")) {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }
}
