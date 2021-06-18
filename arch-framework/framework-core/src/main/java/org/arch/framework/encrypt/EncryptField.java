package org.arch.framework.encrypt;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于数据库实体类字段加解密的注解, 让此注解生效需要在类上注解 {@link EncryptClass} .<br>
 * 注意: 注解的字段如果是对象, <br>
 * 1. 加密时, 先转换为 json 字符串再加密, 以 json 字符串存储; <br>
 * 2. 解密时, 先解密再包 json 字符串转换为对象.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 14:55
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EncryptField {

    /**
     * as type
     */
    @AliasFor("encryptType")
    String value() default "";

    /**
     * 加解密类型, 目前支持: 保留格式加解密算法(FPE), 随机 salt hash 算法(BCRYPT), 文本加解密算法(TEXT_ENCRYPT),
     * 实现 {@link EncryptService} 可支持更多加密类型
     */
    @AliasFor("value")
    String encryptType() default "";

    /**
     * 对符合此正则表达式的字段值进行加解密,
     */
    String filterRegx() default "";

    /**
     * 当为 true 时, 表明此字段值是身份证格式, 当 encryptType=FPE 时有效,
     */
    boolean idCard() default false;

}
