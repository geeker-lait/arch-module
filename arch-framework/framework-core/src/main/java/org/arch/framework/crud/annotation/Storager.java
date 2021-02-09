package org.arch.framework.crud.annotation;

import org.arch.framework.crud.sharding.StoragerType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Lait on 2017/7/10.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Storager {
    /**
     * 提供根据对像指定的存储策略进行存储到指定类型的数据库中，如若没有设置，默认使用mysql，可设置多个(数据会双落)
     * @see StoragerType
     */
    StoragerType[] storage() default {StoragerType.MYSQL};

}
