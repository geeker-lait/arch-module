package org.arch.framework.encrypt.impl;

import org.arch.framework.encrypt.EncryptType;
import org.arch.framework.encrypt.EncryptService;
import org.arch.framework.ums.properties.EncryptProperties;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * TextEncryptor
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 17:03
 */
public class TextEncryptorEncryptServiceImpl implements EncryptService {

    private final TextEncryptor textEncryptor;

    public TextEncryptorEncryptServiceImpl(EncryptProperties encryptProperties) {
        EncryptProperties.EncryptorProperties properties = encryptProperties.getTextEncryptor();
        this.textEncryptor = Encryptors.text(properties.getPassword(), properties.getSalt());
    }

    @Override
    @NonNull
    public String encode(@NonNull String rawStr) {
        return textEncryptor.encrypt(rawStr);
    }

    @Override
    @NonNull
    public String decode(@NonNull String encodeStr) {
        return textEncryptor.decrypt(encodeStr);
    }

    @Override
    @NonNull
    public String getEncryptType() {
        return EncryptType.TEXT_ENCRYPT.name();
    }
}
