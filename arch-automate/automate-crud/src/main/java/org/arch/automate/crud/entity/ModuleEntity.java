package org.arch.automate.crud.entity;

import lombok.Data;

@Data
public class ModuleEntity {

    private Long id;
    private Long pid;
    private Long projectId;

    private String artifactId;
    private String groupId;
    private String version;
    private String type;
    private String scope;
    private String pattern;
    private String documentTyps;
}
