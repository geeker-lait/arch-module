package org.arch.alarm.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lait.zhang@gmail.com
 * @description: 规则属性：属性名+中文描述
 * 如name=fundedAmount，desc=实收金额
 * @weixin PN15855012581
 * @date 4/29/2021 2:25 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RegProperty {
    String value() default "";

    String name() default "";

    String desc() default "";
}
