package org.arch.framework.beans.schema.api;

import lombok.Data;
import lombok.experimental.Accessors;
import org.arch.framework.beans.Metadata;
import org.arch.framework.beans.schema.Import;
import org.arch.framework.beans.schema.Pkg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@Accessors(chain = true)
public class Interfac implements Metadata, Import, Pkg {
    /**
     * 接口类目
     */
    private String name;
    /**
     * 接口描述
     */
    private String descr;
    /**
     * 接口包
     */
    private String pkg;
    /**
     * api 类似于模块包, 默认值为 {@link Api#getName()}
     */
    private String api;
    /**
     * api 类似于模块包描述, 默认值为 {@link Api#getDescr()}
     */
    private String apiDescr;
    /**
     * 泛型类型: String -> <String>, String, Object -> <String, Object>, Map<String, Object> -> <Map<String, Object>>
     */
    private String genericTyp;
    /**
     * 需要导入的包
     */
    private final Set<String> imports = new HashSet<>();
    /**
     * 接口注解
     */
    private final Set<Annot> annotations = new HashSet<>();
    /**
     * 请求方法
     */
    private final List<Curl> curls = new ArrayList<>();
    /**
     * 存储从入参数中解析出的字段 Model 类型
     */
    private final Set<Model> inModels = new HashSet<>();
    /**
     * 存储从出参数中解析出的字段 Model 类型
     */
    private final Set<Model> outModels = new HashSet<>();
}
