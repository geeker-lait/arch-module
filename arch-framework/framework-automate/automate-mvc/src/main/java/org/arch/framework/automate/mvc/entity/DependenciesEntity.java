package org.arch.framework.automate.mvc.entity;

import lombok.Data;

@Data
public class DependenciesEntity {
    private String artifactId;
    private String groupId;
    private String version;
    private String type;
}
