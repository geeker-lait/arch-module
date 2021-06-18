package org.arch.framework.encrypt.impl;

import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.encrypt.EncryptType;
import org.arch.framework.encrypt.EncryptService;
import org.arch.framework.encrypt.TOTP;
import org.arch.framework.ums.properties.EncryptProperties;
import org.arch.framework.utils.SecurityUtils;
import org.springframework.lang.NonNull;

import java.time.Instant;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;

/**
 * TOTP 动态密码(二次密码)
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 17:03
 */
public class TotpEncryptServiceImpl implements EncryptService {

    public static final long ZERO = 0L;
    private final EncryptProperties.TOTPProperties properties;

    public TotpEncryptServiceImpl(EncryptProperties encryptProperties) {
        this.properties = encryptProperties.getTotp();
    }

    @Override
    @NonNull
    public String encode(@NonNull String rawStr) {
        String key = properties.getKey();
        long slidingWindow = properties.getSlidingWindow().getSeconds();
        if (!hasText(key)) {
            key = SecurityUtils.getJwtString();
            if (isNull(key)) {
                throw new AuthenticationException(AuthStatusCode.UNAUTHORIZED);
            }
        }
        long now = Instant.now().getEpochSecond();
        long t = (now - ZERO) / slidingWindow;
        String time = Long.toHexString(t).toUpperCase();
        while (time.length() < 16) {
            //noinspection StringConcatenationInLoop,AlibabaStringConcat
            time = "0" + time;
        }
        return TOTP.generateTOTP(key, "" + slidingWindow, properties.getReturnDigits(), properties.getCrypto());
    }

    @Override
    @NonNull
    public String decode(@NonNull String encodeStr) {
        return encodeStr;
    }

    @Override
    @NonNull
    public String getEncryptType() {
        return EncryptType.TOTP.name();
    }
}
