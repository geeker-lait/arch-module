package org.arch.framework.crud.annotation;

import java.lang.annotation.*;

/**
 * Created by Lait on 2017/8/4.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Sharding {
    String key();
}
