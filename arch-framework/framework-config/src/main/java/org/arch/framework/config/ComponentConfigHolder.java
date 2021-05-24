package org.arch.framework.config;


import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.ServiceLoader;

@Slf4j
public class ComponentConfigHolder {
    public static ComponentConfig MID_CONFIG;

    public ComponentConfigHolder() {

    }

    public static ComponentConfig get() {
        return MID_CONFIG;
    }

    static {
        ServiceLoader<ComponentConfig> serviceLoader = ServiceLoader.load(ComponentConfig.class);

        for (Iterator iterator = serviceLoader.iterator(); iterator.hasNext(); MID_CONFIG = (ComponentConfig) iterator.next()) {
        }

        if (MID_CONFIG == null) {
            log.error("未找到midconfig实现", new ComponentInitException("未找到实现"));
        }

    }
}
