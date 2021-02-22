package org.arch.framework.automate.generater.config;

import lombok.Data;
import org.arch.framework.automate.generater.config.properties.*;
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
 *       </p>
 *       {
 * 	"cover": true,
 * 	"template": {
 * 		"dir": "src/main/resources/templates"
 *        },
 * 	"database": {
 * 		"password": "root",
 * 		"tables": [{
 * 			"columns": [{
 * 				"length": "",
 * 				"name": "",
 * 				"typ": ""
 *            }],
 * 			"name": "",
 * 			"comment": ""
 *        }],
 * 		"driver": "com.mysql.cj.jdbc.Driver",
 * 		"username": "root"
 *    },
 * 	"author": "lait",
 * 	"project": {
 * 		"path": "",
 * 		"pom": {
 * 			"groupId": "",
 * 			"artifactId": "",
 * 			"version": "",
 * 			"modules": [{
 * 				"groupId": "",
 * 				"artifactId": "",
 * 				"packages": "entity,dao,service",
 * 				"version": "",
 * 				"modules": [{
 * 					"groupId": "",
 * 					"artifactId": "",
 * 					"packages": "entity,dao,service",
 * 					"version": ""
 *                }, {
 * 					"groupId": "",
 * 					"artifactId": "",
 * 					"packages": "entity,dao,service",
 * 					"version": "",
 * 					"modules": [{
 * 						"groupId": "",
 * 						"artifactId": "",
 * 						"packages": "entity,dao,service",
 * 						"version": ""
 *                    }, {
 * 						"groupId": "",
 * 						"artifactId": "",
 * 						"packages": "entity,dao,service",
 * 						"version": ""
 *                    }]
 *                }],
 * 				"dependencies": [{
 * 					"groupId": "",
 * 					"artifactId": ""
 *                }]
 *            }]
 *        }
 *    },
 * 	"packages": [{
 * 		"template": "",
 * 		"id": "entity",
 * 		"suffix": "",
 * 		"pkg": ""
 *    }, {
 * 		"template": "",
 * 		"id": "mapper",
 * 		"suffix": "",
 * 		"pkg": ""
 *    }, {
 * 		"template": "",
 * 		"id": "dao",
 * 		"suffix": "",
 * 		"pkg": ""
 *    }, {
 * 		"template": "",
 * 		"id": "service",
 * 		"suffix": "",
 * 		"pkg": ""
 *    }, {
 * 		"template": "",
 * 		"id": "controller",
 * 		"suffix": "",
 * 		"pkg": ""
 *    }, {
 * 		"template": "",
 * 		"id": "dto",
 * 		"suffix": "",
 * 		"pkg": ""
 *    }]
 * }
 * @weixin PN15855012581
 * @date :
 */
@Data
@Component
@ConfigurationProperties(prefix="spring.generator")
public class GeneratorConfig {
    private String author;
    // 是否覆盖
    private boolean cover;
    // 模版配置
    private TemplateProperties template;
    // 项目配置
    private ProjectProperties project;
    // Excel数据源 生产项目
    private ExcelProperties excel;
    // Db数据源 生产仙姑
    private DatabaseProperties database;
    // 包集合
    private List<PackageProperties> packages;
}
