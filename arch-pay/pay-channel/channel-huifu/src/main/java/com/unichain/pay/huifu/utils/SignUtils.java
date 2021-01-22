//package com.unichain.pay.huifu.utils;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
///**
// * @author: PC
// * @version: v1.0
// * @description: com.unichain.pay.base.payapi.huifupay.utils
// * @date:2019/3/26
// */
//public class SignUtils {
//
//    public static String hmacsha256(String plaintext, String appKey) {
//
//        SecretKeySpec signingKey = new SecretKeySpec(appKey.getBytes(), "HmacSHA256");
//
//        Mac mac = null;
//        try {
//            mac = Mac.getInstance("HmacSHA256");
//            mac.init(signingKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return byte2hex(mac.doFinal(plaintext.getBytes()));
//
//    }
//
//
//    private static String byte2hex(byte[] b) {
//        StringBuilder hs = new StringBuilder();
//        String stmp;
//        for (int n = 0; b != null && n < b.length; n++) {
//            stmp = Integer.toHexString(b[n] & 0XFF);
//            if (stmp.length() == 1) {
//                hs.append('0');
//            }
//            hs.append(stmp);
//        }
//        return hs.toString();
//    }
//
//    public static void main(String[] args) {
//        String plainText = "GEThttps://mertest.cloudpnr.com/core/eloan/v1/query/card/binapp_token=app-1e986de6-9fe0-46c7-9acd-52e39ea21932&card_no=6230580000162115401&client_name=test_company_wmkj&company_name=YLPH&request_date=20190327&request_seq=33798e4ffc544956bf1507e182ebc8de";
//        String appKey = "b5e410ae43798a08c9afb2c4adc36f03";
//        System.out.println(hmacsha256(plainText,appKey));
//    }
//
//}
