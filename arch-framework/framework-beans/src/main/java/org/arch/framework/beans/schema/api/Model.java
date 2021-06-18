package org.arch.framework.beans.schema.api;

import lombok.Data;
import lombok.experimental.Accessors;
import org.arch.framework.beans.schema.Import;
import org.arch.framework.beans.schema.Pkg;

import java.util.HashSet;
import java.util.Set;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.29 10:00
 */
@Data
@Accessors(chain = true)
public class Model implements Import, Pkg {

    private String name;
    private String descr;
    private String pkg;
    /**
     * api 类似于模块包
     */
    private String api;
    /**
     * 泛型类型: String -> <String>, String, Object -> <String, Object>, Map<String, Object> -> <Map<String, Object>>
     */
    private String genericTyp;
    private final Set<Annot> annotations = new HashSet<>();
    private final Set<Param> fields = new HashSet<>();
    private final Set<String> imports = new HashSet<>();
    /**
     * 从 {@link Param} 中解析出的字段 {@link Model} 类型
     */
    private final Set<Model> paramModels = new HashSet<>();

}
