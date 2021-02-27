package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.framework.automate.generater.core.DataProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * poms:
 *   - artifactId:
 *     groupId:
 *     version:
 *     packageTypes: entity,dao,service
 *     dependencies:
 *          - groupId:
 *            artifactId:
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PomProperties implements DataProperties {

    private String groupId;
    private String artifactId;
    private String version;
    // pom 类型 pom/jar
    private String packaging;
    // 包类型，即该pom模块下有哪些文档类型
    private String documentTypes;
    // 是否跟节点
    private boolean root;
    // 父节点
    @NestedConfigurationProperty
    private DependencieProterties parent;
    // 子模块
    @NestedConfigurationProperty
    private List<PomProperties> modules;
    // 依赖
    @NestedConfigurationProperty
    private List<DependencieProterties> dependencies;
    // 依赖管理
    @NestedConfigurationProperty
    private List<DependencieProterties> dependencyManagement ;

}
