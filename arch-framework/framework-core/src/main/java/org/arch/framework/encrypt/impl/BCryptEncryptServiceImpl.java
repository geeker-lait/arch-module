package org.arch.framework.encrypt.impl;

import org.arch.framework.encrypt.EncryptType;
import org.arch.framework.encrypt.EncryptService;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCryptPasswordEncoder 的实现了添加随机 salt 算法，并且能从hash后的字符串中获取 salt 进行原始密码与hash后的密码的对比
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 17:03
 */
@SuppressWarnings({"AlibabaClassNamingShouldBeCamel"})
public class BCryptEncryptServiceImpl implements EncryptService {

    private static final String PREFIX = "{bcrypt}";
    private final BCryptPasswordEncoder passwordEncoder;

    public BCryptEncryptServiceImpl() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @NonNull
    public String encode(@NonNull String rawStr) {
        return PREFIX + passwordEncoder.encode(rawStr);
    }

    @Override
    @NonNull
    public String decode(@NonNull String encodeStr) {
        return encodeStr;
    }

    @Override
    @NonNull
    public String getEncryptType() {
        return EncryptType.BCRYPT.name();
    }
}
