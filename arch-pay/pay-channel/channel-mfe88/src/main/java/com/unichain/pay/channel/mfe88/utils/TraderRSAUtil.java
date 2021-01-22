package com.unichain.pay.channel.mfe88.utils;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * RSA签名公共类
 */
public class TraderRSAUtil {

    private static TraderRSAUtil instance;

    private TraderRSAUtil() {

    }

    public static TraderRSAUtil getInstance() {
        if (null == instance)
            return new TraderRSAUtil();
        return instance;
    }

    /**
     * 签名处理
     *
     * @param privateKey ：私钥
     * @param sign_str   ：签名源内容
     * @return
     */
    public String sign(PrivateKey privateKey, String sign_str) {
        try {
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature.getInstance("SHA1WithRSA");
            signet.initSign(privateKey);
            signet.update(sign_str.getBytes(StandardCharsets.UTF_8));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sign(String signStr, String pfxPath, String pwd) throws Exception {
        PrivateKey privateKey = KeyUtil.getPrivateKey(pfxPath, "pfx", pwd);
        TraderRSAUtil rsa = TraderRSAUtil.getInstance();
        String sign = rsa.signRSA(privateKey, signStr);
        return sign;
    }

    /**
     * 请求参数签名
     *
     * @param tranData 业务数据
     * @return
     * @throws Exception
     */
    public String sign(String signStr, byte[] pfx, String pwd) throws Exception {
        PrivateKey privateKey = KeyUtil.getPrivateKey(new ByteArrayInputStream(pfx), "pfx", pwd);
        TraderRSAUtil rsa = TraderRSAUtil.getInstance();
        String sign = rsa.signRSA(privateKey, signStr);
        return sign;
    }

    /**
     * 签名处理
     *
     * @param prikeyvalue ：私钥
     * @param sign_str    ：签名源内容
     * @return
     */
    public String signRSA2(String prikeyvalue, String sign_str) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Util.getBytesBASE64(prikeyvalue));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature.getInstance("SHA256WithRSA");
            signet.initSign(myprikey);
            signet.update(sign_str.getBytes(StandardCharsets.UTF_8));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签名处理
     *
     * @param prikeyvalue ：私钥
     * @param sign_str    ：签名源内容
     * @return
     */
    public String signRSA(String prikeyvalue, String sign_str) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Util.getBytesBASE64(prikeyvalue));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature.getInstance("SHA128WithRSA");
            signet.initSign(myprikey);
            signet.update(sign_str.getBytes(StandardCharsets.UTF_8));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签名处理
     *
     * @param privateKey ：私钥
     * @param sign_str   ：签名源内容
     * @return
     */
    public String signRSA(PrivateKey privateKey, String sign_str) {
        try {
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature.getInstance("SHA256WithRSA");
            signet.initSign(privateKey);
            signet.update(sign_str.getBytes(StandardCharsets.UTF_8));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean valiSign(String signStr, String certPath, String respSign) {
        try {
            TraderRSAUtil rsa = TraderRSAUtil.getInstance();
            PublicKey publicKey = KeyUtil.getPublicKey(certPath, "RSA");
            return rsa.checkRsaSign(publicKey, signStr, respSign);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean valiSign(String signStr, byte[] cert, String rspSign) {
        try {
            TraderRSAUtil rsa = TraderRSAUtil.getInstance();
            PublicKey publicKey = KeyUtil.getPublicKey(new ByteArrayInputStream(cert), "RSA");
            return rsa.checkRsaSign(publicKey, signStr, rspSign);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 签名验证
     *
     * @param pubKey  ：公钥
     * @param obj_str ：源字符串 参数按照accsii排序（升序）
     * @param sign    ：签名结果串
     * @return
     */
    public boolean checkRsaSign(String pubKey, String obj_str, String sign) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Util.getBytesBASE64(pubKey));
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(priPKCS8);
            byte[] signed = Base64Util.getBytesBASE64(sign);// 这是SignatureData输出的数字签
            java.security.Signature signetcheck = java.security.Signature.getInstance("SHA128WithRSA");
            signetcheck.initVerify(publicKey);
            signetcheck.update(obj_str.getBytes(StandardCharsets.UTF_8));
            return signetcheck.verify(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 签名验证
     *
     * @param publicKey ：公钥
     * @param obj_str   ：源字符串 参数按照accsii排序（升序）
     * @param sign      ：签名结果串
     * @return
     */
    public boolean checkRsaSign(PublicKey publicKey, String obj_str, String sign) {
        try {
            byte[] signed = Base64Util.getBytesBASE64(sign);// 这是SignatureData输出的数字签
            java.security.Signature signetcheck = java.security.Signature.getInstance("SHA256WithRSA");
            signetcheck.initVerify(publicKey);
            signetcheck.update(obj_str.getBytes(StandardCharsets.UTF_8));
            return signetcheck.verify(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 签名验证
     *
     * @param pubKey  ：公钥
     * @param obj_str ：源字符串 参数按照accsii排序（升序）
     * @param sign    ：签名结果串
     * @return
     */
    public boolean checkRsa2Sign(String pubKey, String obj_str, String sign) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Util.getBytesBASE64(pubKey));
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(priPKCS8);
            byte[] signed = Base64Util.getBytesBASE64(sign);// 这是SignatureData输出的数字签
            java.security.Signature signetcheck = java.security.Signature.getInstance("SHA256WithRSA");
            signetcheck.initVerify(publicKey);
            signetcheck.update(obj_str.getBytes(StandardCharsets.UTF_8));
            return signetcheck.verify(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 加载私钥匙
     *
     * @param filePath
     * @param fileType
     * @return
     * @throws Exception
     */
    public PrivateKey getPrivateKey(String filePath, String fileType, String psw) throws Exception {

        try {
            return KeyUtil.getPrivateKey(filePath, fileType, psw);
        } catch (Exception e) {
            throw new Exception("私钥加载异常");
        }
    }

}
