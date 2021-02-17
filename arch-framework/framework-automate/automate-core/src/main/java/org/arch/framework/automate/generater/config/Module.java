package org.arch.framework.automate.generater.config;

import lombok.Data;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description:
 *
 * artifactId:
 * groupId:
 * version:
 * packages: entity,dao,service
 * modules:
 * @weixin PN15855012581
 * @date :
 */
@Data
public class Module {
    private String artifactId;
    private String groupId;
    private String version;
    private String packages;
    private List<Module> modules;
}
