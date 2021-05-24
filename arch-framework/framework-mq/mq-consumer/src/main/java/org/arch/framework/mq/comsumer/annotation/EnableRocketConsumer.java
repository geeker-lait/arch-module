package org.arch.framework.mq.comsumer.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableRocketConsumer {
    /**
     * 消费组名称 即Group
     * @return
     */
    String value();
}
