package org.arch.framework.config.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Field {
    String label() default "";

    String description() default "";

    int order() default 999;
}
