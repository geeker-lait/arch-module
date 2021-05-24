package org.arch.framework.mq.comsumer;

import org.arch.framework.spi.Componentable;

public class RocketmqConsumerComponent implements Componentable {
    private final static String name = "rocketmq-consumer-spring";

    @Override
    public String getComponentName() {
        return name;
    }
}
