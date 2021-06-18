package org.arch.framework.encrypt.impl;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.encrypt.EncryptType;
import org.arch.framework.encrypt.EncryptService;
import org.arch.framework.encrypt.FF3Cipher;
import org.arch.framework.ums.properties.EncryptProperties;
import org.springframework.lang.NonNull;

/**
 * FPE(FF3) 保留格式的加解密, 非常适合数字类型的加解密; 当然也适合字母加数字的加解密, 但需要重新设置
 * {@link EncryptProperties.FF3Properties#getRadix()} (10(数字), 26(数字加前16个小写字母), 36(数字加26个小写字母)).
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 17:03
 */
@Slf4j
public class Ff3EncryptServiceImpl implements EncryptService {

    private final FF3Cipher ff3Cipher;

    public Ff3EncryptServiceImpl(FF3Cipher ff3Cipher) {
        this.ff3Cipher = ff3Cipher;
    }

    @Override
    @NonNull
    public String encode(@NonNull String rawStr) {
        try {
            return ff3Cipher.encrypt(rawStr);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return rawStr;
        }
    }

    @Override
    @NonNull
    public String decode(@NonNull String encodeStr) {
        try {
            return ff3Cipher.decrypt(encodeStr);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return encodeStr;
        }
    }

    @Override
    @NonNull
    public String encodeIdCard(@NonNull String rawStr) {
        int len = rawStr.length();
        String rawPrefixStr = rawStr.substring(0, len - 1);
        // 最后一为不参与加解密
        String lastChar = rawStr.substring(len - 1);
        return encode(rawPrefixStr) + lastChar;
    }

    @Override
    @NonNull
    public String decodeIdCard(@NonNull String encodeStr) {
        int len = encodeStr.length();
        String encodePrefixStr = encodeStr.substring(0, len - 1);
        // 最后一为不参与加解密
        String lastChar = encodeStr.substring(len - 1);
        return decode(encodePrefixStr) + lastChar;
    }

    @Override
    @NonNull
    public String getEncryptType() {
        return EncryptType.FPE.name();
    }
}
