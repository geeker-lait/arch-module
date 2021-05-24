package org.arch.framework.mq.producer;

import org.arch.framework.spi.Componentable;

public class RocketmqProducerComponent implements Componentable {
    private static final String name = "rocketmq-producer-spring";

    public RocketmqProducerComponent() {
    }

    public String getComponentName() {
        return "rocketmq-producer-spring";
    }
}
