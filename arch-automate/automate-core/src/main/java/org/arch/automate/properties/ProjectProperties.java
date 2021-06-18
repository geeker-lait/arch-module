package org.arch.automate.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.automate.core.ConfigProperties;
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
 * packages: entity,dao,converter
 * dependencies:
 * - groupId:
 * artifactId:
 * poms:
 * - artifactId:
 * groupId:
 * version:
 * packages: entity,dao,converter
 * @weixin PN15855012581
 * @date :
 */
@Data
@NoArgsConstructor
@ToString
public class ProjectProperties implements ConfigProperties {
    /**
     * 是否覆盖
     */
    private Boolean cover;
    private String author;
    private String path;
    private String name;
    private String basePkg;
    private Boolean domain = true;
    // 默认开启后缀
    private Boolean stuffixed = true;
    @NestedConfigurationProperty
    private PomProperties pom;


    public Path getProjectRootPath() {
        if (name == null) {
            name = pom.getArtifactId();
        }
        return Paths.get(path + File.separator + name);
    }
}
