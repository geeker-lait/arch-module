package org.arch.framework.automate.xmind.module;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * project
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.3 18:57
 */
@Data
@Accessors(chain = true)
public class Project {
    private String name;
    private String descr;
    private final List<Module> modules = new ArrayList<>();
}
