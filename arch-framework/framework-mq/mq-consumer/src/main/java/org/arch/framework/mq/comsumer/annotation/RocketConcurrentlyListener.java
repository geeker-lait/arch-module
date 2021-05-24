package org.arch.framework.mq.comsumer.annotation;

import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface RocketConcurrentlyListener {

    /**
     * topic 主题
     * @return
     */
    String value();

    String description() default "";

    int consumeThreadMin() default 20;

    int consumeThreadMax() default 64;

    ConsumeFromWhere consumeFromWhere() default ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET;

    /**
     * 指定订阅的Rocketmq集群，这里的取来自海星平台的配置。默认值为null，可不填，对应的海星平台配置也不填即可（这里为null是为了兼容性）。
     * 这里取值应该具有集群语义，如 shanghai-xxx-rocketmq-cluster，fuzhou-xxx-rocketmq-cluster
     * 注意： 这里的取值需要和后端海星配置一直。
     * @return
     */

    String withCluster() default "";
}
