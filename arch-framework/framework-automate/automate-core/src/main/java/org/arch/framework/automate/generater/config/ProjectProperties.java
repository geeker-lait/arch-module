package org.arch.framework.automate.generater.config;

import lombok.Data;
import org.arch.framework.automate.generater.properties.ConfigModel;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description:
 *  project:
 *       path:
 *       poms:
 *         - artifactId:
 *           groupId:
 *           version:
 *           packages: entity,dao,service
 *           dependencies:
 *             - groupId:
 *               artifactId:
 *           poms:
 *             - artifactId:
 *               groupId:
 *               version:
 *               packages: entity,dao,service
 * @weixin PN15855012581
 * @date :
 */
@Data
public class ProjectProperties {
    private String path;
    private String basePkg;
    private List<PomProperties> poms;

}
