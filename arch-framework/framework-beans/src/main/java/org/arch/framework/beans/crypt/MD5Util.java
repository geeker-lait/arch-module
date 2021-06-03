package org.arch.framework.beans.crypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.arch.framework.beans.utils.StringUtils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * date: 2020/3/30 18:33
 * desc:
 */
public class MD5Util {

    private static final int MD5INDEX = 16 ;

    public static void main(String[] args) throws Exception {
        String pwd = MD5("123456", "yueshang1005");
        System.out.println(pwd);
    }

    public static String MD5(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update((password + "{" + salt + "}").getBytes(Charset.forName("UTF-8")));
        char[] hexDigits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] tmp = instance.digest();
        char[] str = new char[16 * 2];
        int k = 0;
        for (int i = 0; i < MD5INDEX; i++) {
            byte byte0 = tmp[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);

    }

    /**
     * 签名字符串
     *
     * @param text         需要签名的字符串
     * @param key          密钥
     * @param inputCharset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String inputCharset) {
        //拼接key
        text = text + key;
        return DigestUtils.md5Hex(StringUtils.getContentBytes(text, inputCharset));
    }

    /**
     * 签名字符串
     *
     * @param text         需要签名的字符串
     * @param sign         签名结果
     * @param key          密钥
     * @param inputCharset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String inputCharset) {
        //判断是否一样
        return StringUtils.equals(sign(text, key, inputCharset).toUpperCase(), sign.toUpperCase());
    }



}
