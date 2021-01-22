package com.unichain.pay.channel.lianlian.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * SHA256withRSA签名公共类
 *
 * @author shmily
 */
public class SHA256withRSAUtil {
    private static Logger log = LoggerFactory.getLogger(SHA256withRSAUtil.class);
    private static SHA256withRSAUtil instance;
    private static String SHA256withRSA = "SHA256withRSA";

    private SHA256withRSAUtil() {

    }

    public static SHA256withRSAUtil getInstance() {
        if (null == instance) {
            return new SHA256withRSAUtil();
        }
        return instance;
    }

    /**
     * 签名处理
     *
     * @param prikeyvalue：私钥文件
     * @param sign_str：签名源内容
     * @return
     */
    public static String sign(String prikeyvalue, String sign_str) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getBytesBASE64(prikeyvalue));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            Signature signet = Signature.getInstance(SHA256withRSA);
            signet.initSign(myprikey);
            signet.update(sign_str.getBytes(StandardCharsets.UTF_8));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(signed));
        } catch (Exception e) {
            log.error("签名失败{}", e.getMessage());
        }
        return null;
    }

    /**
     * 签名验证
     *
     * @param pubkeyvalue：公钥
     * @param oid_str：源串
     * @param signed_str：签名结果串
     * @return
     */
    public static boolean checksign(String pubkeyvalue, String oid_str, String signed_str) {
        try {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64.getBytesBASE64(pubkeyvalue));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
            byte[] signed = Base64.getBytesBASE64(signed_str);// 这是SignatureData输出的数字签名
            Signature signetcheck = Signature.getInstance(SHA256withRSA);
            signetcheck.initVerify(pubKey);
            signetcheck.update(oid_str.getBytes(StandardCharsets.UTF_8));
            return signetcheck.verify(signed);
        } catch (Exception e) {
            log.error("签名验证异常{}", e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        String sign = SHA256withRSAUtil.sign("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJPb6UtHkRtCmunLtxgWUUkqKVMqdMrvLxU4UzTRaNddI2tHUszyTSntfz+l1S3BjRBvjx1/yvrFRvneW7lmM9w+e5LPUnIhqnNrl2aeioOJWHz+Ba6qrRXz8kCf6kfsAMG4H2A2xMcb26ZiMPZxFKHinuKcW7bT+bXTFxrQsR/JAgMBAAECgYEAh2vK6F/LzyPZrngeYblPCavL3ZftEFCw1saXrrB9TYLIheD1PTBO7C/RdAH2lcnH4V3LvkDlL3iv4Pp/F/c7Vvvgs/LbpXwnPvYVtdkZ1x3AZRfS/5uSrSoAkiN0zEJnmb3Ywp7YlCYfVlke4u6dhQN+WxvqPl69VMBzNpagXWECQQDlBVUvIqQp6e0Gsp4oOj3HyQtCT+BsaRZkLtMNTq5pcz/83s1H0cIoU8dTT7LCZvRw+yjYgQ5YBY9D0CZBmwdfAkEApUbzmt2klNpf2apadyI+fYcbYBky3kb2q6YZ/xQuCU8eSJC4F2bPDDfxpsIqADj5A8KB74EnB6h1UT9rQONx1wJBAMXuFfDmv3p58aAYPxgFPd+soU5uOkd3iyKKVVzq41G/iU3CQSgQ4Px5a4tVFeltkVUTu/lhkEQCig7Rlj6c/YECQHwqUIrQ5nsZj5bDv1Du/glp/ev1Il0Q7PHJSJB0RZ2ivbqAVnzmNLgWM0o3ZjxikNj9QIaA/aRoLzLJtTa7aGMCQFEkTk/9gIYYKolMwMllO/SN+dO54W1Pc/Dx65ZsEwzgq+UEBb0BjbxbVebVRcaXam6OKIuCW2KwdQuMlY6AqeQ=", "123");
        System.out.println(sign);
        System.out.print(SHA256withRSAUtil.checksign("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT2+lLR5EbQprpy7cYFlFJKilTKnTK7y8VOFM00WjXXSNrR1LM8k0p7X8/pdUtwY0Qb48df8r6xUb53lu5ZjPcPnuSz1JyIapza5dmnoqDiVh8/gWuqq0V8/JAn+pH7ADBuB9gNsTHG9umYjD2cRSh4p7inFu20/m10xca0LEfyQIDAQAB", "123", sign));
    }
}
