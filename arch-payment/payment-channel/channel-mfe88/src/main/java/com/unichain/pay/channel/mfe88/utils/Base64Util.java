package com.unichain.pay.channel.mfe88.utils;


import sun.misc.BASE64Decoder;

import java.nio.charset.StandardCharsets;

public class Base64Util {
    public static String getBASE64(String s) {
        if (s == null)
            return null;
        return (new sun.misc.BASE64Encoder()).encode(s.getBytes(StandardCharsets.UTF_8));
    }

    public static String getBASE64(byte[] b) {
        return (new sun.misc.BASE64Encoder()).encode(b);
    }


    public static String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] getBytesBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return b;
        } catch (Exception e) {
            return null;
        }
    }
}
