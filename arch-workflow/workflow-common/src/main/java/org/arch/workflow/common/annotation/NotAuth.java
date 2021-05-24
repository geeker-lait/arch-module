package org.arch.workflow.common.annotation;

import java.lang.annotation.*;


/**
 * 无须认证
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月12日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface NotAuth {

}
