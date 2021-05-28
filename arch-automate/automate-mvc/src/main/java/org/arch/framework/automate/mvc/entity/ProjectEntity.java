package org.arch.framework.automate.mvc.entity;

import lombok.Data;

@Data
public class ProjectEntity {
    //
    private Long id;
    private Boolean cover;
    private String author;
    private Boolean domain;
    private String path;
    private String basePkg;
}
