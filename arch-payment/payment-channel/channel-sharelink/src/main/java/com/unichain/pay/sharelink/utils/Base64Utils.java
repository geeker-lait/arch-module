package com.unichain.pay.sharelink.utils;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Base64Utils {
    public static Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     *
     * @param base64 base64
     * @return 源二进制数据
     */
    public static byte[] decode(String base64) {
        byte[] base64s = new byte[1024];
        base64s = Base64.decodeBase64(base64);
        return base64s;
    }

    /**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     *
     * @param bytes base64
     * @return BASE64后的二进制数据
     */
    public static String encode(byte[] bytes) {
        byte[] c = new byte[1024];
        c = Base64.encodeBase64(bytes);
        return new String(c);
    }

}
