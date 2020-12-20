package com.unichain.pay.channel.mfe88.utils;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Enumeration;


/**
 * <strong>Title : KeyUtil</strong><br>
 * <strong>Description : 加密工具类</strong><br>
 * <strong>Create on : 2014-4-29</strong><br>
 *
 * @author linda1@cmbc.com.cn<br>
 */
public abstract class KeyUtil {

    private final static byte[] hex = "0123456789abcdef".getBytes();
    private static String BASE64 = "base64";
    private static String HEX = "hex";

    /**
     * 数字签名函数入口
     *
     * @param plainBytes    待签名明文字节数组
     * @param privateKey    签名使用私钥
     * @param signAlgorithm 签名算法
     * @return 签名后的字节数组
     * @throws Exception
     */
    public static byte[] digitalSign(byte[] plainBytes, PrivateKey privateKey, String signAlgorithm) throws Exception {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(privateKey);
            signature.update(plainBytes);
            byte[] signBytes = signature.sign();

            return signBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(String.format("数字签名时没有[%s]此类算法", signAlgorithm));
        } catch (InvalidKeyException e) {
            throw new Exception("数字签名时私钥无效");
        } catch (SignatureException e) {
            throw new Exception("数字签名时出现异常");
        }
    }

    /**
     * RSA加密
     *
     * @param plainBytes      明文字节数组
     * @param publicKey       公钥
     * @param keyLength       密钥bit长度
     * @param reserveSize     padding填充字节数，预留11字节
     * @param cipherAlgorithm 加解密算法，一般为RSA/ECB/PKCS1Padding
     * @return 加密后字节数组，不经base64编码
     * @throws Exception
     */
    public static byte[] RSAEncrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm)
            throws Exception {
        int keyByteSize = keyLength / 8; // 密钥字节数
        int encryptBlockSize = keyByteSize - reserveSize; // 加密块大小=密钥字节数-padding填充字节数
        int nBlock = plainBytes.length / encryptBlockSize;// 计算分段加密的block数，向上取整
        if ((plainBytes.length % encryptBlockSize) != 0) { // 余数非0，block数再加1
            nBlock += 1;
        }

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // 输出buffer，大小为nBlock个keyByteSize
            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
            // 分段加密
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }
                // 得到分段加密结果
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                // 追加结果到输出buffer中
                outbuf.write(encryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(String.format("没有[%s]此类加密算法", cipherAlgorithm));
        } catch (NoSuchPaddingException e) {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        } catch (InvalidKeyException e) {
            throw new Exception("无效密钥");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("加密块大小不合法");
        } catch (BadPaddingException e) {
            throw new Exception("错误填充模式");
        } catch (IOException e) {
            throw new Exception("字节输出流异常");
        }
    }

    /**
     * RSA解密
     *
     * @param encryptedBytes  加密后字节数组
     * @param privateKey      私钥
     * @param keyLength       密钥bit长度
     * @param reserveSize     padding填充字节数，预留11字节
     * @param cipherAlgorithm 加解密算法，一般为RSA/ECB/PKCS1Padding
     * @return 解密后字节数组，不经base64编码
     * @throws Exception
     */
    public static byte[] RSADecrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize, String cipherAlgorithm)
            throws Exception {
        int keyByteSize = keyLength / 8; // 密钥字节数
        int decryptBlockSize = keyByteSize - reserveSize; // 解密块大小=密钥字节数-padding填充字节数
        int nBlock = encryptedBytes.length / keyByteSize;// 计算分段解密的block数，理论上能整除

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 输出buffer，大小为nBlock个decryptBlockSize
            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
            // 分段解密
            for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
                // block大小: decryptBlock 或 剩余字节数
                int inputLen = encryptedBytes.length - offset;
                if (inputLen > keyByteSize) {
                    inputLen = keyByteSize;
                }

                // 得到分段解密结果
                byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
                // 追加结果到输出buffer中
                outbuf.write(decryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(String.format("没有[%s]此类解密算法", cipherAlgorithm));
        } catch (NoSuchPaddingException e) {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        } catch (InvalidKeyException e) {
            throw new Exception("无效密钥");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("解密块大小不合法");
        } catch (BadPaddingException e) {
            throw new Exception("错误填充模式");
        } catch (IOException e) {
            throw new Exception("字节输出流异常");
        }
    }

    /**
     * 生成随机密钥
     *
     * @param size 位数
     * @return
     */
    public static String generateRandomKey(int size) {
        StringBuilder key = new StringBuilder();
        String chars = "0123456789ABCDEF";
        for (int i = 0; i < size; i++) {
            int index = (int) (Math.random() * (chars.length() - 1));
            key.append(chars.charAt(index));
        }
        return key.toString();
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encodeBASE64(byte[] content) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(content);
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decodeBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * 3DES解密
     *
     * @param key     密钥
     * @param content 待解密信息
     * @return
     * @throws Exception
     */
    public static byte[] decode3DES(byte[] key, byte[] content) throws Exception {
        // 不是8的倍数的，补足
        if (key.length % 8 != 0) {
            int groups = key.length / 8 + (key.length % 8 != 0 ? 1 : 0);
            byte[] temp = new byte[groups * 8];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, key.length);
            key = temp;
        }
        // 长度为16位，转换成24位的密钥
        if (key.length == 16) {
            byte[] temp = new byte[24];
            System.arraycopy(key, 0, temp, 0, key.length);
            System.arraycopy(key, 0, temp, key.length, temp.length - key.length);
            key = temp;
        }

        // 不是8的倍数的，补足
        byte[] srcBytes = content;
        if (srcBytes.length % 8 != 0) {
            int groups = content.length / 8 + (content.length % 8 != 0 ? 1 : 0);
            srcBytes = new byte[groups * 8];
            Arrays.fill(srcBytes, (byte) 0);
            System.arraycopy(content, 0, srcBytes, 0, content.length);
        }

        SecretKey deskey = new SecretKeySpec(key, "DESede");
        Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] tgtBytes = cipher.doFinal(srcBytes);
        return tgtBytes;
    }

    /**
     * DES加密
     *
     * @param key     密钥信息
     * @param content 待加密信息
     * @return
     * @throws Exception
     */
    public static byte[] encodeDES(byte[] key, byte[] content) throws Exception {
        // 不是8的倍数的，补足
        if (key.length % 8 != 0) {
            int groups = key.length / 8 + (key.length % 8 != 0 ? 1 : 0);
            byte[] temp = new byte[groups * 8];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, key.length);
            key = temp;
        }

        // 不是8的倍数的，补足
        byte[] srcBytes = content;
        if (srcBytes.length % 8 != 0) {
            int groups = content.length / 8 + (content.length % 8 != 0 ? 1 : 0);
            srcBytes = new byte[groups * 8];
            Arrays.fill(srcBytes, (byte) 0);
            System.arraycopy(content, 0, srcBytes, 0, content.length);
        }

        IvParameterSpec iv = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0});
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv, sr);
        byte[] tgtBytes = cipher.doFinal(srcBytes);
        return tgtBytes;
    }

    /**
     * DES解密
     *
     * @param key     密钥信息
     * @param content 待加密信息
     * @return
     * @throws Exception
     */
    public static byte[] decodeDES(byte[] key, byte[] content) throws Exception {
        // 不是8的倍数的，补足
        if (key.length % 8 != 0) {
            int groups = key.length / 8 + (key.length % 8 != 0 ? 1 : 0);
            byte[] temp = new byte[groups * 8];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, key.length);
            key = temp;
        }
        // 不是8的倍数的，补足
        byte[] srcBytes = content;
        if (srcBytes.length % 8 != 0) {
            int groups = content.length / 8 + (content.length % 8 != 0 ? 1 : 0);
            srcBytes = new byte[groups * 8];
            Arrays.fill(srcBytes, (byte) 0);
            System.arraycopy(content, 0, srcBytes, 0, content.length);
        }

        IvParameterSpec iv = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0});
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv, sr);
        byte[] tgtBytes = cipher.doFinal(content);
        return tgtBytes;
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     * @author zhangwl
     */
    public static PublicKey getPublicKey(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return getPublicKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入缓存流出错");
            }

            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     * @author zhangwl
     */
    public static PublicKey getPublicKey(String publicKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从文件中加载公钥
     *
     * @param filePath 证书文件路径
     * @param fileType 文件类型
     * @throws Exception 加载公钥时产生的异常
     * @author zhangwl
     */
    public static PublicKey getPublicKey(String filePath, String fileType) throws Exception {
        InputStream in = new FileInputStream(filePath);
        try {
            if (!"PEM".equalsIgnoreCase(fileType)) {
                Certificate cert = null;
                CertificateFactory factory = CertificateFactory.getInstance("X.509");
                cert = factory.generateCertificate(in);
                return cert.getPublicKey();
            } else {
                return getPublicKey(in);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    public static PublicKey getPublicKey(InputStream in, String fileType) throws Exception {
        try {
            if (!"PEM".equalsIgnoreCase(fileType)) {
                Certificate cert = null;
                CertificateFactory factory = CertificateFactory.getInstance("X.509");
                cert = factory.generateCertificate(in);
                return cert.getPublicKey();
            } else {
                return getPublicKey(in);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件名
     * @return 是否成功
     * @throws Exception
     * @author zhangwl
     */
    public static PrivateKey getPrivateKey(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return getPrivateKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入缓存流出错");
            }

            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr 公钥数据字符串
     * @throws Exception 加载私钥时产生的异常
     * @author zhangwl
     */
    public static PrivateKey getPrivateKey(String privateKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (IOException e) {
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param filePath 证书文件路径
     * @param fileType 文件类型
     * @throws Exception 加载公钥时产生的异常
     * @author zhangwl
     */
    public static PrivateKey getPrivateKey(String filePath, String fileType, String passwd) throws Exception {
        PrivateKey key = null;
        InputStream in = new FileInputStream(filePath);

        String keyType = "JKS";
        if ("keystore".equalsIgnoreCase(fileType)) {
            keyType = "JKS";
        } else if ("pfx".equalsIgnoreCase(fileType)) {
            keyType = "PKCS12";
        } else if ("pem".equalsIgnoreCase(fileType)) {
            keyType = "PEM";
        }

        try {
            if (!"PEM".equalsIgnoreCase(keyType)) {
                KeyStore ks = KeyStore.getInstance(keyType);
                char[] cPasswd = passwd.toCharArray();
                ks.load(in, cPasswd);
                Enumeration<String> aliasenum = ks.aliases();
                String keyAlias = null;
                while (aliasenum.hasMoreElements()) {
                    keyAlias = aliasenum.nextElement();
                    key = (PrivateKey) ks.getKey(keyAlias, cPasswd);
                    if (key != null)
                        break;
                }
                return key;
            } else {
                return getPrivateKey(in);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    public static PrivateKey getPrivateKey(InputStream in, String fileType, String passwd) throws Exception {
        PrivateKey key = null;
        String keyType = "JKS";
        if ("keystore".equalsIgnoreCase(fileType)) {
            keyType = "JKS";
        } else if ("pfx".equalsIgnoreCase(fileType)) {
            keyType = "PKCS12";
        } else if ("pem".equalsIgnoreCase(fileType)) {
            keyType = "PEM";
        }

        try {
            if (!"PEM".equalsIgnoreCase(keyType)) {
                KeyStore ks = KeyStore.getInstance(keyType);
                char[] cPasswd = passwd.toCharArray();
                ks.load(in, cPasswd);
                Enumeration<String> aliasenum = ks.aliases();
                String keyAlias = null;
                while (aliasenum.hasMoreElements()) {
                    keyAlias = aliasenum.nextElement();
                    key = (PrivateKey) ks.getKey(keyAlias, cPasswd);
                    if (key != null)
                        break;
                }
                return key;
            } else {
                return getPrivateKey(in);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    /**
     * RAS加密
     *
     * @param peerPubKey 公钥
     * @param data       待加密信息
     * @return byte[]
     * @throws Exception
     * @author zhangwl
     */
    public static byte[] encryptRSA(RSAPublicKey peerPubKey, byte[] data, boolean useBase64Code, String charset) throws Exception {
        String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
        int KEYBIT = 2048;
        int RESERVEBYTES = 11;
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段加密的block数 (向上取整)
        int nBlock = (data.length / encryptBlock);
        if ((data.length % encryptBlock) != 0) { // 余数非0，block数再加1
            nBlock += 1;
        }
        // 输出buffer, 大小为nBlock个decryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlock);
        cipher.init(Cipher.ENCRYPT_MODE, peerPubKey);
        // cryptedBase64Str =
        // Base64.encodeBase64String(cipher.doFinal(plaintext.getBytes()));
        // 分段加密
        for (int offset = 0; offset < data.length; offset += encryptBlock) {
            // block大小: encryptBlock 或 剩余字节数
            int inputLen = (data.length - offset);
            if (inputLen > encryptBlock) {
                inputLen = encryptBlock;
            }
            // 得到分段加密结果
            byte[] encryptedBlock = cipher.doFinal(data, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(encryptedBlock);
        }
        // 如果是Base64编码，则返回Base64编码后的数组
        if (useBase64Code) {
            return Base64.encodeBase64String(outbuf.toByteArray()).getBytes(charset);
        } else {
            return outbuf.toByteArray(); // ciphertext
        }
    }

    /**
     * RSA解密
     *
     * @param localPrivKey 私钥
     * @param cryptedBytes 待解密信息
     * @return byte[]
     * @throws Exception
     * @author zhangwl
     */
    public static byte[] decryptRSA(RSAPrivateKey localPrivKey, byte[] cryptedBytes, boolean useBase64Code, String charset) throws Exception {
        String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
        byte[] data = null;

        // 如果是Base64编码的话，则要Base64解码
        if (useBase64Code) {
            data = Base64.decodeBase64(new String(cryptedBytes, charset));
        } else {
            data = cryptedBytes;
        }

        int KEYBIT = 2048;
        int RESERVEBYTES = 11;
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段解密的block数 (理论上应该能整除)
        int nBlock = (data.length / decryptBlock);
        // 输出buffer, , 大小为nBlock个encryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * encryptBlock);
        cipher.init(Cipher.DECRYPT_MODE, localPrivKey);
        // plaintext = new
        // String(cipher.doFinal(Base64.decodeBase64(cryptedBase64Str)));
        // 分段解密
        for (int offset = 0; offset < data.length; offset += decryptBlock) {
            // block大小: decryptBlock 或 剩余字节数
            int inputLen = (data.length - offset);
            if (inputLen > decryptBlock) {
                inputLen = decryptBlock;
            }

            // 得到分段解密结果
            byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(decryptedBlock);
        }
        outbuf.flush();
        outbuf.close();
        return outbuf.toByteArray();
    }

    /**
     * RSA签名
     *
     * @param localPrivKey 需要签名的信息
     * @return byte[]
     * @throws Exception
     * @author zhangwl
     */
    public static byte[] signRSA(RSAPrivateKey localPrivKey, byte[] plainBytes, boolean useBase64Code, String charset) throws Exception {
        String SIGNATURE_ALGORITHM = "SHA1withRSA";
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(localPrivKey);
        signature.update(plainBytes);

        // 如果是Base64编码的话，需要对签名后的数组以Base64编码
        if (useBase64Code) {
            return Base64.encodeBase64String(signature.sign()).getBytes(charset);
        } else {
            return signature.sign();
        }
    }

    /**
     * 签名函数入口
     *
     * @param alg          算法
     * @param localPrivKey 私钥
     * @param plainBytes   需要签名的信息
     * @param encode       编码信息
     * @throws Exception
     */
    public static String sign(String alg, PrivateKey localPrivKey, byte[] plainBytes, String encode) throws Exception {
        Signature signature = Signature.getInstance(alg);
        signature.initSign(localPrivKey);
        signature.update(plainBytes);
        // 如果是Base64编码的话，需要对签名后的数组以Base64编码
        if (BASE64.equalsIgnoreCase(encode)) {
            return Base64.encodeBase64String(signature.sign());
        } else if (HEX.equalsIgnoreCase(encode)) {
            return bytesToHexString(signature.sign());
        } else {
            return signature.sign().toString();
        }
    }

    private static String bytesToHexString(byte[] b) {
        byte[] buff = new byte[2 * b.length];
        for (int i = 0; i < b.length; i++) {
            buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
            buff[2 * i + 1] = hex[b[i] & 0x0f];
        }
        return new String(buff);
    }

    /**
     * 验签操作
     *
     * @param peerPubKey 公钥
     * @param plainBytes 需要验签的信息
     * @param signBytes  签名信息
     * @return boolean
     * @author zhangwl
     */
    public static boolean verifyRSA(RSAPublicKey peerPubKey, byte[] plainBytes, byte[] signBytes, boolean useBase64Code, String charset)
            throws Exception {
        boolean isValid = false;
        String SIGNATURE_ALGORITHM = "SHA1withRSA";
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(peerPubKey);
        signature.update(plainBytes);

        // 如果是Base64编码的话，需要对验签的数组以Base64解码
        if (useBase64Code) {
            isValid = signature.verify(Base64.decodeBase64(new String(signBytes, charset)));
        } else {
            isValid = signature.verify(signBytes);
        }
        return isValid;
    }

    /**
     * 验签操作
     *
     * @param alg        算法
     * @param peerPubKey 证书对象
     * @param plainBytes 需要验签的信息
     * @param signStr    签名信息
     * @param encode     编码信息
     * @return boolean
     * @author zhangwl
     */
    public static boolean verify(String alg, PublicKey peerPubKey, byte[] plainBytes, String signStr, String encode) throws Exception {
        boolean isValid = false;
        Signature signature = Signature.getInstance(alg);
        signature.initVerify(peerPubKey);
        signature.update(plainBytes);
        // 如果是Base64编码的话，需要对签名后的数组以Base64编码
        if (BASE64.equalsIgnoreCase(encode)) {
            isValid = signature.verify(Base64.decodeBase64(signStr));
        } else if (HEX.equalsIgnoreCase(encode)) {
            isValid = signature.verify(hexStringToBytes(signStr));
        } else {
            isValid = signature.verify(signStr.getBytes());
        }
        return isValid;
    }

    private static byte[] hexStringToBytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a') {
            return (c - 'a' + 10) & 0x0f;
        }
        if (c >= 'A') {
            return (c - 'A' + 10) & 0x0f;
        }
        return (c - '0') & 0x0f;
    }

    /**
     * AES加密
     *
     * @param plainBytes    明文字节数组
     * @param keyBytes      对称密钥字节数组
     * @param useBase64Code 是否使用Base64编码
     * @param charset       编码格式
     * @return byte[]
     */
    public static byte[] encryptAES(byte[] plainBytes, byte[] keyBytes, boolean useBase64Code, String charset) throws Exception {
        String cipherAlgorithm = "AES/CBC/PKCS5Padding";
        String keyAlgorithm = "AES";
        String IV = "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

            byte[] encryptedBlock = cipher.doFinal(plainBytes);

            if (useBase64Code) {
                return Base64.encodeBase64String(encryptedBlock).getBytes(charset);
            } else {
                return encryptedBlock;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("AES加密失败");
        }
    }

    /**
     * AES解密
     *
     * @param cryptedBytes  密文字节数组
     * @param keyBytes      对称密钥字节数组
     * @param useBase64Code 是否使用Base64编码
     * @param charset       编码格式
     * @return byte[]
     */
    public static byte[] decryptAES(byte[] cryptedBytes, byte[] keyBytes, boolean useBase64Code, String charset) throws Exception {
        String cipherAlgorithm = "AES/CBC/PKCS5Padding";
        String keyAlgorithm = "AES";
        String IV = "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";

        byte[] data = null;

        // 如果是Base64编码的话，则要Base64解码
        if (useBase64Code) {
            data = Base64.decodeBase64(new String(cryptedBytes, charset));
        } else {
            data = cryptedBytes;
        }

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

            byte[] decryptedBlock = cipher.doFinal(data);

            return decryptedBlock;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("AES解密失败");
        }
    }


    /**
     * 字符数组转16进制字符串
     *
     * @param str
     * @return
     */
    public static byte[] string2bytes(String str, int radix) {
        byte[] srcBytes = str.getBytes();

        // 2个16进制字符占用1个字节，8个二进制字符占用1个字节
        int size = 2;
        if (radix == 2) {
            size = 8;
        }

        byte[] tgtBytes = new byte[srcBytes.length / size];
        for (int i = 0; i < srcBytes.length; i = i + size) {
            String tmp = new String(srcBytes, i, size);
            tgtBytes[i / size] = (byte) Integer.parseInt(tmp, radix);
        }
        return tgtBytes;
    }

}
