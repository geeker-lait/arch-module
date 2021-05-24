package org.arch.framework.mq.consumer.autoconfig;

import org.arch.framework.mq.comsumer.processor.RocketmqConsumerBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RocketmqConsumerBeanPostProcessor.class})
public class RocketmqConsumerAutoConfiguration {
    public RocketmqConsumerAutoConfiguration() {
    }
}
