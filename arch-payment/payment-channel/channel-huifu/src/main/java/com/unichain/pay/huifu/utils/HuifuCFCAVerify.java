//package com.unichain.pay.huifu.utils;
//
//import cfca.sadk.algorithm.mvc.PKIException;
//import cfca.sadk.lib.crypto.Session;
//import cfca.sadk.util.Signature;
//import cfca.sadk.x509.certificate.X509Cert;
//import cfca.sadk.x509.certificate.X509CertVerifier;
//import com.huifu.saturn.cfca.Base64;
//import com.huifu.saturn.cfca.CFCAException;
//import com.huifu.saturn.cfca.VerifyEnum;
//import com.huifu.saturn.cfca.VerifyResult;
//import com.huifu.saturn.cfca.util.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.InputStream;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
///**
// * 公钥解密相关重写
// *
// * @author
// * @date 2018/12/8 16:19
// */
//public class HuifuCFCAVerify {
//
//    private static Logger logger = LoggerFactory.getLogger(HuifuCFCAVerify.class);
//
//
//    //存公钥相关 merchantNo 作为key
//    private static ConcurrentMap<String, X509Cert> x509CertSignature = new ConcurrentHashMap<>();
//
//
//    /**
//     * 存入私钥
//     *
//     * @param merchantNo
//     * @param cerPath
//     */
//    public static void putPublicKey(String merchantNo, String cerPath) throws Exception {
//        //流方式读取
//        ClassPathResource x509CertResource = new ClassPathResource(cerPath);
//        InputStream x509Certfile = x509CertResource.getInputStream();
//        X509Cert cert = new X509Cert(x509Certfile);
//        if (cert != null) {
//            x509CertSignature.putIfAbsent(merchantNo, cert);
//        }
//
//    }
//
//    public static void verifyCerDate(X509Cert userX509Cert) throws CFCAException {
//        if (!X509CertVerifier.verifyCertDate(userX509Cert)) {
//            logger.warn("Cert out of date:" + userX509Cert.getNotBefore() + "---" + userX509Cert.getNotAfter());
//            throw new CFCAException(VerifyEnum.CERT_EXPRIED);
//        }
//        logger.info("userX509Cert date is valid:" + userX509Cert.getNotBefore() + "---" + userX509Cert.getNotAfter());
//    }
//
//    public static void verifyCer(X509Cert userX509Cert, String merchantNo) throws CFCAException {
//        try {
//            X509Cert x509Cert = x509CertSignature.get(merchantNo);
//            if (x509Cert != null) {
//                X509CertVerifier.updateTrustCertsMap(x509Cert);
//                if (!X509CertVerifier.validateCertSign(userX509Cert)) {
//                    logger.warn("userX509Cert is wrong!");
//                    throw new CFCAException(VerifyEnum.CERT_ILLEGAL);
//                }
//                logger.info("userX509Cert is right!");
//            }
//        } catch (PKIException var3) {
//            logger.error("verifyCer error:", var3);
//            throw new CFCAException(VerifyEnum.FAILED);
//        }
//    }
//
//
//
//
//
//
//    public static void verifyMer(Signature sigUtil, String merId, byte[] bytes) throws CFCAException {
//        try {
//            String subject = sigUtil.getSignerX509CertFromP7SignData(bytes).getSubject();
//            if (StringUtils.isBlank(merId)) {
//                logger.warn("merId is blank. merId=" + merId);
//                throw new CFCAException(VerifyEnum.MER_FAILED);
//            } else if (!subject.contains(merId)) {
//                logger.warn("subject not contains merId. subject=" + subject + ",merId=" + merId);
//                throw new CFCAException(VerifyEnum.MER_FAILED);
//            }
//        } catch (PKIException var4) {
//            logger.error("verifyMer error:", var4);
//            throw new CFCAException(VerifyEnum.FAILED);
//        }
//    }
//
//    public static VerifyResult verifyP7VerifyMessageAttach(Signature sigUtil, byte[] bytes, Session session) throws CFCAException {
//        try {
//            boolean isSign = sigUtil.p7VerifyMessageAttach(bytes, session);
//            if (!isSign) {
//                throw new CFCAException(VerifyEnum.SIGN_ERROR);
//            } else {
//                logger.info("p7 dig alg with verification: " + sigUtil.getDigestAlgorithm());
//                logger.info("p7 cert subject with verification:" + sigUtil.getSignerCert().getSubject());
//                logger.info("p7 signature: " + new String(Base64.encodeBase64(sigUtil.getSignature())));
//                logger.info("RSA P7 attach verify OK!");
//                VerifyResult verifyResult = new VerifyResult(VerifyEnum.SUCCESS);
//                verifyResult.setContent(sigUtil.getSourceData());
//                return verifyResult;
//            }
//        } catch (PKIException var5) {
//            logger.error("verifyP7VerifyMessageAttach error:", var5);
//            throw new CFCAException(VerifyEnum.FAILED);
//        }
//    }
//
//}
