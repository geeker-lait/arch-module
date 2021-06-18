package org.arch.framework.encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 14:57
 */
public enum EncryptType {
    /**
     * {@link FF3Cipher} : 适合保留格式的对称加解密, 如, 手机号, 身份证号, 卡号.
     */
    FPE,
    /**
     * {@link BCryptPasswordEncoder}: 实现了添加随机 salt 算法，并且能从hash后的字符串中获取 salt 进行原始密码与hash后的密码的对比
     */
    BCRYPT,
    /**
     * {@link TextEncryptor}: 对文本进行对称性加解密.
     */
    TEXT_ENCRYPT,
    /**
     * {@link TOTP}: 动态密码算法, 适合进行二次密码验证, 不适用与对数据库字段的加解密
     */
    TOTP,

}
