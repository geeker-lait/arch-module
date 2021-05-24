package org.arch.framework.config;

import org.arch.framework.spi.Componentable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class ComponentHolder {
    public static List<Componentable> COLLECT_COMPONENT_ITERATOR;

    public ComponentHolder() {
    }

    public static List<Componentable> get() {
        return COLLECT_COMPONENT_ITERATOR;
    }

    static {
        ServiceLoader<Componentable> serviceLoader = ServiceLoader.load(Componentable.class);
        Iterator<Componentable> iterator = serviceLoader.iterator();
        ArrayList componentList = new ArrayList();

        while (iterator.hasNext()) {
            componentList.add(iterator.next());
        }

        COLLECT_COMPONENT_ITERATOR = componentList;
    }
}
