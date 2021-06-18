package org.arch.framework.encrypt;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于数据库实体类字段需要加解密时的注解在类上.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 14:55
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EncryptClass {

}
