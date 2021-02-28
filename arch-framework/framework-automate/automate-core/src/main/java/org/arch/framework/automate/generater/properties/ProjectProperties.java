package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.framework.automate.generater.core.SchemaProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lait.zhang@gmail.com
 * @description: project:
 * path:
 * poms:
 * - artifactId:
 * groupId:
 * version:
 * packages: entity,dao,service
 * dependencies:
 * - groupId:
 * artifactId:
 * poms:
 * - artifactId:
 * groupId:
 * version:
 * packages: entity,dao,service
 * @weixin PN15855012581
 * @date :
 */
@Data
@NoArgsConstructor
@ToString
public class ProjectProperties implements SchemaProperties {
    /**
     * 是否覆盖
     */
    private Boolean cover;
    private String author;
    private String path;
    private String name;
    private String basePkg;
    private Boolean domain = true;
    @NestedConfigurationProperty
    private PomProperties pom;


    public Path getProjectRootPath() {
        if (name == null) {
            name = pom.getArtifactId();
        }
        return Paths.get(path + File.separator + name);
    }
}
