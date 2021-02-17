package org.arch.framework.automate.generater.config;

import lombok.Data;

@Data
public class DependencieProterties implements DataProperties {
    private String groupId;
    private String artifactId;
    private String version;
}
