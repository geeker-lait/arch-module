package org.arch.framework.encrypt;
/**
Copyright (c) 2011 IETF Trust and the persons identified as
authors of the code. All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, is permitted pursuant to, and subject to the license
terms contained in, the Simplified BSD License set forth in Section
4.c of the IETF Trust's Legal Provisions Relating to IETF Documents
(http://trustee.ietf.org/license-info).
 */

import org.springframework.lang.NonNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;

/**
 *
 * This is an OATH TOTP algorithm. Visit
 * www.openauthentication.org for more information.
 * <PRE>
 *    // 时延兼容: 由于网络的原因，客户端生成密码的时间和服务器接受密码的时间可能差距会很大，很有可能使得这 2 个时间不在同一个步长内。
 *    // 当一个动态密码产生在一个步长的结尾，服务器收到的密码很有可能在下一个步长的开始。
 *    // 验证系统应该设置一个策略允许动态密码的传输时延，不应该只验证当前步长的动态密码，
 *    // 推荐最多设置一个时延窗口来兼容传输延时。
 *    long zero = 0L;
 *    long slidingWindow = 30L;
 *
 *    long now = Instant.now().getEpochSecond();
 *    long t = (now - zero) / slidingWindow;
 *    String time = Long.toHexString(t).toUpperCase();
 *    while (time.length() < 16)
 *        time = "0" + time;
 *    String dynamicPassword = generateTOTP(key, time, 8, "HmacSHA1");
 *    try {
 *        // ... 解码逻辑,
 *        return;
 *    }
 *    catch (Exception e) {
 *        // 解码逻辑出错, 滑动窗口再次解码
 *        t = (now - slidingWindow) / slidingWindow;
 *        time = Long.toHexString(t).toUpperCase();
 *        while (time.length() < 16)
 *            time = "0" + time;
 *        dynamicPassword = generateTOTP(key, time, 8, "HmacSHA1");
 *    }
 * </PRE>
 *
 * @author Johan Rydell, PortWise, Inc.
 */
 
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
public class TOTP {
 
    private TOTP() {
    }
 
    /**
     * This method uses the JCE to provide the crypto algorithm. HMAC computes a
     * Hashed Message Authentication Code with the crypto hash algorithm as a
     * parameter.
     * 
     * @param crypto
     *            : the crypto algorithm (HmacSHA1, HmacSHA256, HmacSHA512)
     * @param keyBytes
     *            : the bytes to use for the HMAC key
     * @param text
     *            : the message or text to be authenticated
     */
    @NonNull
    private static byte[] hmac_sha(@NonNull String crypto, @NonNull byte[] keyBytes, @NonNull byte[] text) {
        try {
            Mac hmac;
            hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }
 
    /**
     * This method converts a HEX string to Byte[]
     * 
     * @param hex
     *            : the HEX string
     * 
     * @return: a byte array
     */
    @NonNull
    private static byte[] hexStr2Bytes(@NonNull String hex) {
        // Adding one byte to get the right conversion
        // Values starting with "0" can be converted
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();
 
        // Copy all the REAL bytes, not the "first"
        byte[] ret = new byte[bArray.length - 1];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = bArray[i + 1];
        }
        return ret;
    }
 
    private static final int[] DIGITS_POWER
    // 0 1 2 3 4 5 6 7 8
    = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };
 
    /**
     * This method generates a TOTP value for the given set of parameters.
     * 
     * @param key
     *            : the shared secret, HEX encoded
     * @param time
     *            : a value that reflects a time
     * @param returnDigits
     *            : number of digits to return
     * 
     * @return: a numeric String in base 10 that includes
     *          {@code truncationDigits} digits
     */
    @NonNull
    public static String generateTOTP(@NonNull String key, @NonNull String time, @NonNull Integer returnDigits) {
        return generateTOTP(key, time, returnDigits, "HmacSHA1");
    }
 
    /**
     * This method generates a TOTP value for the given set of parameters.
     * 
     * @param key
     *            : the shared secret, HEX encoded
     * @param time
     *            : a value that reflects a time
     * @param returnDigits
     *            : number of digits to return
     * 
     * @return: a numeric String in base 10 that includes
     *          {@code truncationDigits} digits
     */
    @NonNull
    public static String generateTOTP256(@NonNull String key, @NonNull String time, @NonNull Integer returnDigits) {
        return generateTOTP(key, time, returnDigits, "HmacSHA256");
    }
 
    /**
     * This method generates a TOTP value for the given set of parameters.
     * 
     * @param key
     *            : the shared secret, HEX encoded
     * @param time
     *            : a value that reflects a time
     * @param returnDigits
     *            : number of digits to return
     * 
     * @return: a numeric String in base 10 that includes
     *          {@code truncationDigits} digits
     */
    @NonNull
    public static String generateTOTP512(@NonNull String key, @NonNull String time, @NonNull Integer returnDigits) {
        return generateTOTP(key, time, returnDigits, "HmacSHA512");
    }
 
    /**
     * This method generates a TOTP value for the given set of parameters.
     * 
     * @param key
     *            : the shared secret, HEX encoded
     * @param time
     *            : a value that reflects a time
     * @param returnDigits
     *            : number of digits to return
     * @param crypto
     *            : the crypto function to use
     * 
     * @return: a numeric String in base 10 that includes
     *          {@code truncationDigits} digits
     */
    @NonNull
    public static String generateTOTP(@NonNull String key, @NonNull String time,
                                      @NonNull Integer returnDigits, @NonNull String crypto) {
        String result = null;
 
        // Using the counter
        // First 8 bytes are for the movingFactor
        // Compliant with base RFC 4226 (HOTP)
        while (time.length() < 16) {
            //noinspection StringConcatenationInLoop
            time = "0" + time;
        }
 
        // Get the HEX in a Byte[]
        byte[] msg = hexStr2Bytes(time);
        byte[] k = hexStr2Bytes(key);
        byte[] hash = hmac_sha(crypto, k, msg);
 
        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;
 
        int binary = ((hash[offset] & 0x7f) << 24)
                | ((hash[offset + 1] & 0xff) << 16)
                | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);
 
        int otp = binary % DIGITS_POWER[returnDigits];
 
        result = Integer.toString(otp);
        while (result.length() < returnDigits) {
            //noinspection StringConcatenationInLoop
            result = "0" + result;
        }
        return result;
    }
}
