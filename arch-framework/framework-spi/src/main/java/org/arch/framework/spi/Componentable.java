package org.arch.framework.spi;

public interface Componentable {
    default String getComponentVersion() {
        return "2020.05.24";
    }

    String getComponentName();
}
