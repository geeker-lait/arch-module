package org.arch.framework.automate.xmind.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.29 10:00
 */
@Data
@Accessors(chain = true)
public class Entity {

    private String name;
    private String descr;
    private String pkg;
    private final List<Annot> annotations = new ArrayList<>();
    private final List<Param> fields = new ArrayList<>();
    private final List<String> imports = new ArrayList<>();

}
