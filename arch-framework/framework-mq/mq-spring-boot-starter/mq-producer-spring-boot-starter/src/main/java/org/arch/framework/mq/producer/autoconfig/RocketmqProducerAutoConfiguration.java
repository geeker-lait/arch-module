package org.arch.framework.mq.producer.autoconfig;

import org.arch.framework.mq.producer.processor.RocketmqProducerBeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RocketmqProducerBeanDefinitionRegistryPostProcessor.class})
public class RocketmqProducerAutoConfiguration {
    public RocketmqProducerAutoConfiguration() {
    }
}
