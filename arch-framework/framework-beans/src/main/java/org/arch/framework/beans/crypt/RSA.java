package org.arch.framework.beans.crypt;

import cn.hutool.core.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSA {
    private static final Logger LOG = LoggerFactory.getLogger(RSA.class);
    private static final String ALGORITHM = "RSA";


    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";


    /**
     * RSA签名
     *
     * @param content           待签名数据
     * @param privateKey        私钥
     * @param signAlgorithms    签名算法
     * @param characterEncoding 编码格式
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String signAlgorithms, String characterEncoding) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(signAlgorithms);

            signature.initSign(priKey);
            signature.update(content.getBytes(characterEncoding));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        }
        catch (GeneralSecurityException e) {
            LOG.error("", e);
        }
        catch (UnsupportedEncodingException e) {
            LOG.error("", e);
        }

        return null;
    }


    /**
     * RSA签名
     *
     * @param content           待签名数据
     * @param privateKey        私钥
     * @param signAlgorithms    签名算法
     * @param characterEncoding 编码格式
     * @return 签名值
     */
    public static String sign(String content, PrivateKey privateKey, String signAlgorithms, String characterEncoding) {
        try {
            java.security.Signature signature = java.security.Signature.getInstance(signAlgorithms);
            signature.initSign(privateKey);
            signature.update(content.getBytes(characterEncoding));
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        }
        catch (GeneralSecurityException e) {
            LOG.error("", e);
        }
        catch (UnsupportedEncodingException e) {
            LOG.error("", e);
        }

        return null;
    }


    /**
     * RSA签名
     *
     * @param content           待签名数据
     * @param privateKey        私钥
     * @param characterEncoding 编码格式
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String characterEncoding) {
        return sign(content, privateKey, SIGN_ALGORITHMS, characterEncoding);
    }

    /**
     * RSA签名
     *
     * @param content           待签名数据
     * @param privateKey        私钥
     * @param characterEncoding 编码格式
     * @return 签名值
     */
    public static String sign(String content, PrivateKey privateKey, String characterEncoding) {
        return sign(content, privateKey, SIGN_ALGORITHMS, characterEncoding);
    }

    /**
     * RSA验签名检查
     *
     * @param content           待签名数据
     * @param sign              签名值
     * @param publicKey         公钥
     * @param signAlgorithms    签名算法
     * @param characterEncoding 编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String publicKey, String signAlgorithms, String characterEncoding) {
        try {
            PublicKey pubKey = getPublicKey(publicKey, ALGORITHM);
            java.security.Signature signature = java.security.Signature.getInstance(signAlgorithms);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(characterEncoding));
            return signature.verify(Base64.decode(sign));
        }
        catch (GeneralSecurityException e) {
            LOG.error("", e);
        }
        catch (IOException e) {
            LOG.error("", e);
        }
        return false;
    }

    /**
     * RSA验签名检查
     *
     * @param content           待签名数据
     * @param sign              签名值
     * @param publicKey         公钥
     * @param signAlgorithms    签名算法
     * @param characterEncoding 编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, PublicKey publicKey, String signAlgorithms, String characterEncoding) {
        try {
            java.security.Signature signature = java.security.Signature.getInstance(signAlgorithms);
            signature.initVerify(publicKey);
            signature.update(content.getBytes(characterEncoding));
            return signature.verify(Base64.decode(sign));
        }
        catch (GeneralSecurityException e) {
            LOG.error("", e);
        }
        catch (IOException e) {
            LOG.error("", e);
        }
        return false;
    }

    /**
     * RSA验签名检查
     *
     * @param content           待签名数据
     * @param sign              签名值
     * @param publicKey         公钥
     * @param characterEncoding 编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String publicKey, String characterEncoding) {

        return verify(content, sign, publicKey, SIGN_ALGORITHMS, characterEncoding);
    }


    /**
     * RSA验签名检查
     *
     * @param content           待签名数据
     * @param sign              签名值
     * @param publicKey         公钥
     * @param characterEncoding 编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, PublicKey publicKey, String characterEncoding) {
        return verify(content, sign, publicKey, SIGN_ALGORITHMS, characterEncoding);
    }

    /**
     * 解密
     *
     * @param content           密文
     * @param privateKey        商户私钥
     * @param characterEncoding 编码格式
     * @return 解密后的字符串
     * @throws GeneralSecurityException 解密异常
     * @throws IOException              IOException
     */
    public static String decrypt(String content, String privateKey, String characterEncoding) throws GeneralSecurityException, IOException {
        PrivateKey prikey = getPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, prikey);
        try (InputStream ins = new ByteArrayInputStream(Base64.decode(content)); ByteArrayOutputStream writer = new ByteArrayOutputStream();) {

            //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
            byte[] buf = new byte[128];
            int bufl;
            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;

                if (buf.length == bufl) {
                    block = buf;
                }
                else {
                    block = new byte[bufl];

                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }
                writer.write(cipher.doFinal(block));
            }

            return new String(writer.toByteArray(), characterEncoding);
        }
    }


    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @return 私钥
     * @throws GeneralSecurityException 加密异常
     */
    public static PrivateKey getPrivateKey(String key) throws GeneralSecurityException {

        byte[] keyBytes;
        keyBytes = Base64.decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 得到公钥
     *
     * @param key            密钥字符串（经过base64编码）
     * @param signAlgorithms 密钥类型
     * @return 公钥
     * @throws GeneralSecurityException 加密异常
     * @throws IOException              加密异常
     */
    public static PublicKey getPublicKey(String key, String signAlgorithms) throws GeneralSecurityException, IOException {
        try (ByteArrayInputStream is = new ByteArrayInputStream(key.getBytes("ISO8859-1"))) {
            return getPublicKey(is, signAlgorithms);
        }
    }


    /**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @return 公钥
     * @throws GeneralSecurityException 加密异常
     * @throws IOException              加密异常
     */
    public static PublicKey getPublicKey(String key) throws GeneralSecurityException, IOException {

        return getPublicKey(key, ALGORITHM);
    }

    public static PublicKey getPublicKey(InputStream inputStream, String keyAlgorithm) throws IOException, GeneralSecurityException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));) {
            StringBuilder sb = new StringBuilder();
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                }
                sb.append(readLine);
                sb.append('\r');
            }
            X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decode(sb.toString()));
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            PublicKey publicKey = keyFactory.generatePublic(pubX509);
            return publicKey;
        }
    }

    public static byte[] encrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) throws IOException, GeneralSecurityException {
        int keyByteSize = keyLength / 8;
        int encryptBlockSize = keyByteSize - reserveSize;
        int nBlock = plainBytes.length / encryptBlockSize;
        if ((plainBytes.length % encryptBlockSize) != 0) {
            nBlock += 1;
        }
        try (ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * keyByteSize)) {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                outbuf.write(encryptedBlock);
            }
            outbuf.flush();
            return outbuf.toByteArray();
        }
    }

    public static String encrypt(String content, String publicKey, String cipherAlgorithm, String characterEncoding) throws IOException, GeneralSecurityException {
        return Base64.encode(RSA.encrypt(content.getBytes(characterEncoding), RSA.getPublicKey(publicKey), 1024, 11, cipherAlgorithm));
    }



    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData,
                                             String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher
                        .doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher
                        .doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData,
                                            String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher
                        .doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher
                        .doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = java.util.Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = java.util.Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = java.util.Base64.getDecoder().decode(str);
        //base64编码的私钥
        byte[] decoded = java.util.Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }
}
