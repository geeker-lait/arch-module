package org.arch.framework.automate.generater.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description:
 * spring:
 *   generator:
 *     cover: true
 *     author: lait
 *     template:
 *       dir: src/main/resources/templates
 *     project:
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
 *     packages:
 *       - id: entity
 *         pkg:
 *         template:
 *         suffix:
 *       - id: mapper
 *         pkg:
 *         template:
 *         suffix:
 *       - id: dao
 *         pkg:
 *         template:
 *         suffix:
 *       - id: service
 *         pkg:
 *         template:
 *         suffix:
 *       - id: controller
 *         pkg:
 *         template:
 *         suffix:
 *       - id: dto
 *         pkg:
 *         template:
 *         suffix:
 *     database:
 *       driver: com.mysql.cj.jdbc.Driver
 *       url: "jdbc:mysql://localhost:3306/uni?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
 *       username: root
 *       password: root
 * @weixin PN15855012581
 * @date :
 */
@Data
@Component
@ConfigurationProperties(prefix="spring.generator")
public class GeneratorConfig {
    private String author;
    private boolean cover;
    private TemplateProperties templateProperties;
    private ProjectProperties projectProperties;
    private DatabaseProperties databaseProperties;
    private List<PackageProperties> packages;
}
