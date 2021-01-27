package org.arch.framework.beans.utils;

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



}
