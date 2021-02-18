package org.arch.framework.automate.generater.config;

import lombok.Data;

import java.io.File;

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
public class ProjectProperties {
    private String path;
    private String name;
    private String basePkg;
    private PomProperties pom;


    public File getFile() {
        if (name == null) {
            name = pom.getArtifactId();
        }
        return new File(path + File.separator + name);
    }
}
