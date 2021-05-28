package org.arch.automate.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * project
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.3 18:57
 */
@Data
@Accessors(chain = true)
public class Project {
    private String name;
    private String descr;
    private final Set<Module> modules = new HashSet<>();
}
