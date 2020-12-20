//package com.unichain.pay.huifu.utils;
//
//import cfca.sadk.lib.crypto.JCrypto;
//import cfca.sadk.lib.crypto.Session;
//import cfca.sadk.util.CertUtil;
//import cfca.sadk.util.KeyUtil;
//import cfca.sadk.util.Signature;
//import cfca.sadk.x509.certificate.X509Cert;
//import com.huifu.saturn.cfca.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.InputStream;
//import java.nio.charset.Charset;
//import java.security.PrivateKey;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
///**
// * 重写加密方法 存储私钥
// */
//public class HuifuCFCASignature {
//
//    private static Logger logger = LoggerFactory.getLogger(HuifuCFCASignature.class);
//    private static String mechanism = "sha256WithRSAEncryption";
//
//
//
//    //存公钥 merchantNo 作为key
//    private static ConcurrentMap<String, PrivateKey> privateSignature = new ConcurrentHashMap<>();
//    private static ConcurrentMap<String, X509Cert> x509Signature = new ConcurrentHashMap<>();
//
//
//    /**
//     * 存入私钥
//     * @param merchantNo
//     * @param pfxPath
//     * @param priKeyPass
//     */
//    public static void putPrivateKey(String merchantNo, String pfxPath, String priKeyPass ) throws Exception {
//        //流方式读取  一个流只允许使用一次
//        ClassPathResource classPathResource = new ClassPathResource(pfxPath);
//        InputStream certfile = classPathResource.getInputStream();
//        PrivateKey priKey = KeyUtil.getPrivateKeyFromPFX(certfile, priKeyPass);
//        if (priKey != null){
//            privateSignature.putIfAbsent(merchantNo,priKey);
//        }
//        //
//        ClassPathResource x509CertResource = new ClassPathResource(pfxPath);
//        InputStream x509Certfile = x509CertResource.getInputStream();
//        X509Cert cert = CertUtil.getCertFromPFX(x509Certfile, priKeyPass);
//        if (cert != null){
//            x509Signature.putIfAbsent(merchantNo,cert);
//        }
//
//    }
//
//
//
//
//    public static SignResult signature(String merchantNo, String content, String charset) {
//        return signature(merchantNo, mechanism, content, charset);
//    }
//
//    public static SignResult signature(String merchantNo, String mechanism, String content, String charset) {
//        try {
//            PrivateKey priKey = privateSignature.get(merchantNo);
//            X509Cert cert = x509Signature.get(merchantNo);
//            Signature sigUtil = new Signature();
//            byte[] signature = sigUtil.p7SignMessageAttach(mechanism, content.getBytes(Charset.forName(charset)), priKey, cert, getSession());
//            SignResult signResult = new SignResult(SignEnum.SUCCESS);
//            signResult.setSign(new String(Base64.encodeBase64(signature)));
//            return signResult;
//        } catch (Exception var10) {
//            logger.error("signature error:", var10);
//            return new SignResult(SignEnum.FAILED);
//        }
//    }
//
//
//
//    public static VerifyResult verifyMerSign(String merId, String signature, String charset, String merchantNo) {
//        try {
//            Signature sigUtil = new Signature();
//            byte[] bytes = Base64.decodeBase64(signature.getBytes(Charset.forName(charset)));
//            X509Cert userX509Cert = sigUtil.getSignerX509CertFromP7SignData(bytes);
//            HuifuCFCAVerify.verifyCerDate(userX509Cert);
//            HuifuCFCAVerify.verifyCer(userX509Cert, merchantNo);
//            HuifuCFCAVerify.verifyMer(sigUtil, merId, bytes);
//            return HuifuCFCAVerify.verifyP7VerifyMessageAttach(sigUtil, bytes, getSession());
//        } catch (CFCAException var8) {
//            return new VerifyResult(var8.getCode(), var8.getMessage());
//        } catch (Exception var9) {
//            logger.error("verifyMerSign error:", var9);
//            return new VerifyResult(VerifyEnum.FAILED);
//        }
//    }
//
//
//    public static Session getSession() throws Exception {
//        JCrypto.getInstance().initialize("JSOFT_LIB", (Object)null);
//        return JCrypto.getInstance().openSession("JSOFT_LIB");
//    }
//
//    public static String getMechanism() {
//        return mechanism;
//    }
//
//    public static void setMechanism(String mechanism) {
//        mechanism = mechanism;
//    }
//
//}
