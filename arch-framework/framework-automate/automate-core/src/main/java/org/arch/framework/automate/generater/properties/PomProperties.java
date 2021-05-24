package org.arch.framework.automate.generater.properties;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.framework.automate.generater.core.ConfigProperties;
import org.arch.framework.beans.utils.StringUtils;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * poms:
 * - artifactId:
 * groupId:
 * version:
 * packageTypes: entity,dao,converter
 * dependencies:
 * - groupId:
 * artifactId:
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PomProperties implements ConfigProperties {
    private String groupId;
    private String artifactId;
    private String version;
    // pom 类型 pom/jar
    private String packaging;
    // 包类型，即该pom模块下有哪些文档类型
    private String documentTypes;
    // 是否跟节点
    private boolean root;

    // schema模式 mvc/api 默认为mvc
    private String pattern = "mvc";
    // 父节点
    @NestedConfigurationProperty
    private DependencieProterties parent;
    // 子模块
    @NestedConfigurationProperty
    private final Set<PomProperties> modules = new HashSet<>();
    // 依赖
    @NestedConfigurationProperty
    private final Set<DependencieProterties> dependencies = new HashSet<>();
    // 依赖管理
    @NestedConfigurationProperty
    private final Set<DependencieProterties> dependencyManagement = new HashSet<>();

    @JSONField(serialize = false)
    public Set<String> getDocumentTyps() {
        Set<String> sets = new HashSet<>();
        sets.add("pom");
        if (StringUtils.isNoneBlank(documentTypes)) {
            sets.addAll(Arrays.asList(documentTypes.split(",")));
        }
        return sets;
    }
}
