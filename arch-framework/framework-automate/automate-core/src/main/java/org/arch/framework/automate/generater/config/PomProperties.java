package org.arch.framework.automate.generater.config;

import lombok.Data;

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
public class PomProperties implements DataProperties {

    private String groupId;
    private String artifactId;
    private String version;
    // 包类型，即该pom模块下有哪些包类型
    private String packageTypes;
    // 父节点
    private DependencieProterties parent;
    // 子模块
    private List<PomProperties> modules;
    // 依赖
    private List<DependencieProterties> dependencies;
    // 依赖管理
    private List<DependencieProterties> dependencyManagement;
}
