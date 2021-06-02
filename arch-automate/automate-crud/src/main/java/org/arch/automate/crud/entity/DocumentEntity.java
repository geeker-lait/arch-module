package org.arch.automate.crud.entity;

import lombok.Data;

/**
 * documents:
 * - type: entity
 * pkg: entity
 * template: entity.ftl
 * suffix: Entity
 * - type: mapper
 * pkg: mapper
 * template: mapper.ftl
 * suffix: Mapper
 * - type: dao
 * pkg: dao
 * template: dao.ftl
 * suffix: Dao
 * ext: .java
 * - type: converter
 * pkg: converter
 * template: converter.ftl
 * suffix: Service
 * - type: controller
 * pkg:
 * template: controller.ftl
 * suffix:
 * - type: dto
 * pkg:
 * template: dto.ftl
 * suffix:
 * - type: application
 * pkg:
 * template: application.ftl
 * suffix: Application
 * bootstrap: Test
 * - type: yml
 * pkg:
 * template: yml.ftl
 * ext: .yml
 * bootstrap:
 * suffix:
 * - type: ddl
 * pkg: ddl
 * template: ddl.ftl
 * ext: .sql
 * suffix: -schema
 * - type: docker
 * pkg:
 * template: docker.ftl
 * ext:
 * suffix:
 * - type: api
 * pkg:
 * template: api.ftl
 * ext: .java
 * suffix: Client
 * - type: pom
 * ext:
 * template: pom.ftl
 * suffix:
 */
@Data
public class DocumentEntity {
    private String typ;
    private String ext;
    private String template;
    private String suffix;
    private String pkg;
}
