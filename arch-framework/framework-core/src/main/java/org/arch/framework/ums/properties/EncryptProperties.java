package org.arch.framework.ums.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * NIST认可的Java格式保留加密（ FPE ）FF3算法的实现
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
     * {@link BCryptPasswordEncoder} 算法的属性
     */
    private EncryptorProperties bCrypt = new EncryptorProperties();
    /**
     * {@link TextEncryptor} 算法的属性
     */
    private EncryptorProperties textEncryptor = new EncryptorProperties();


    @Getter
    @Setter
    static class EncryptorProperties {
        /**
         * password
         */
        private String password = "8CE4A06235DACD71AD9CA11C5AF21B8C";
        /**
         * salt
         */
        private String salt = "F6011E95C2EB4B8F";
    }

    @Getter
    @Setter
    static class FF3Properties {
        /**
         * password
         */
        private String password = "8CE4A06235DACD71AD9CA11C5AF21B8C";
        /**
         * salt
         */
        private String salt = "F6011E95C2EB4B8F";
        /**
         * radix: 10(数字), 26(数字加前16个小写字母), 36(数字加26个小写字母)
         */
        private Integer radix = 10;
    }

}
