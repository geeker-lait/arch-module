package org.arch.framework.automate.generater.service.xmind.api;

import lombok.Data;
import lombok.experimental.Accessors;
import org.arch.framework.automate.generater.service.xmind.Import;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.29 10:00
 */
@Data
@Accessors(chain = true)
public class Entity implements Import {

    private String name;
    private String descr;
    private String pkg;
    /** api 类似于模块包 */
    private String api;
    /** 泛型类型: String -> <String>, String, Object -> <String, Object>, Map<String, Object> -> <Map<String, Object>> */
    private String genericTyp;
    private final Set<Annot> annotations = new HashSet<>();
    private final List<Param> fields = new ArrayList<>();
    private final Set<String> imports = new HashSet<>();

}
