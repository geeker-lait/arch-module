package org.arch.framework.automate.xmind.api;

import lombok.Data;
import lombok.experimental.Accessors;
import org.arch.framework.automate.xmind.Import;

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
public class Interfac implements Import {
    // 接口类目
    private String name;
    // 接口描述
    private String descr;
    // 接口包
    private String pkg;
    // 泛型类型: String -> <String>, String, Object -> <String, Object>, Map<String, Object> -> <Map<String, Object>>
    private String genericTyp;
    // 需要导入的包
    private final Set<String> imports = new HashSet<>();
    // 接口注解
    private final Set<Annot> annotations = new HashSet<>();
    // 请求方法
    private final List<Curl> curls = new ArrayList<>();
}
