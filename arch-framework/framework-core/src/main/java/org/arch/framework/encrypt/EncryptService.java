package org.arch.framework.encrypt;

import org.springframework.lang.NonNull;

/**
 * 针对注释 {@link EncryptField} 的加解密服务, 如果需要扩展, 实现此类并注入 IOC 即可
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 16:58
 */
public interface EncryptService {

    /**
     * 加密或 hash
     * @param rawStr    需要加密字符串
     * @return  加密或 hash 后字符串
     */
    @NonNull
    String encode(@NonNull String rawStr);

    /**
     * 加密或 hash, 针对身份证加密
     * @param rawStr    需要加密字符串
     * @return  加密或 hash 后字符串
     */
    @NonNull
    default String encodeIdCard(@NonNull String rawStr) {
        return encode(rawStr);
    }

    /**
     * 解密, hash类算法直接返回 {@code encodeStr}.
     * @param encodeStr    加密或 hash 字符串
     * @return  解密后的字符串, hash类算法直接返回 {@code encodeStr}.
     */
    @NonNull
    String decode(@NonNull String encodeStr);

    /**
     * 解密, hash类算法直接返回 {@code encodeStr}. 针对身份证解密
     * @param encodeStr    加密或 hash 字符串
     * @return  解密后的字符串, hash类算法直接返回 {@code encodeStr}.
     */
    @NonNull
    default String decodeIdCard(@NonNull String encodeStr) {
        return decode(encodeStr);
    }

    /**
     * {@link EncryptType} 名称
     * @return {@link EncryptType#name()}
     */
    @NonNull
    String getEncryptType();

}
