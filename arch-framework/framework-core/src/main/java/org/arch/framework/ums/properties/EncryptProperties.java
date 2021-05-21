package org.arch.framework.ums.properties;

import lombok.Getter;
import lombok.Setter;
import org.arch.framework.encrypt.TOTP;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * 算法的属性
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.18 19:39
 */
@ConfigurationProperties("arch.encrypt")
@Getter
@Setter
public class EncryptProperties {

    /**
     * NIST 认可的 Java 格式保留加密（ FPE ）FF3 算法的属性
     */
    private FF3Properties fpe = new FF3Properties();

    /**
     * {@link TOTP} 基于时间的动态密码算法属性
     */
    private TOTPProperties totp = new TOTPProperties();
    /**
     * {@link TextEncryptor} 算法的属性
     */
    private EncryptorProperties textEncryptor = new EncryptorProperties();


    @Getter
    @Setter
    public static class EncryptorProperties {
        /**
         * password
         */
        private String password = "8CE4A06235DACD71AD9CA11C5AF21B8C";
        /**
         * salt
         */
        private String salt = "F6011E95C2EB4B8F";
    }

    @SuppressWarnings("AlibabaClassNamingShouldBeCamel")
    @Getter
    @Setter
    public static class FF3Properties {
        /**
         * password must be 16, 24, or 32 bytes
         */
        private String password = "8CE4A06235DACD71AD9CA11C5AF21B8C";
        /**
         * tweak, used in each round and split into right and left sides; fixed length: 16.
         */
        private String tweak = "D9C4BC2E11C8FBAA";
        /**
         * radix: 10(数字), 26(数字加前16个小写字母), 36(数字加26个小写字母)
         */
        private Integer radix = 10;
    }

    @Getter
    @Setter
    public static class TOTPProperties {
        /**
         * hash 算法的 key, 可以未 null, null 值时使用 crypto 的算法对用户的 (token)进行 hash.
         */
        private String key;
        /**
         * 支持 hash 算法类型:  HmacSHA1  HmacSHA512   HmacSHA256
         */
        private String crypto = "HmacSHA1";
        /**
         * 返回几位数的动态密码, 默认 8 位.
         */
        private Integer returnDigits = 8;
        /**
         * TOTP 算法的滑动时间窗口长度, 单位: 秒, 默认: 30 秒
         */
        @DurationUnit(ChronoUnit.SECONDS)
        private Duration slidingWindow = Duration.ofSeconds(30);
    }

}
