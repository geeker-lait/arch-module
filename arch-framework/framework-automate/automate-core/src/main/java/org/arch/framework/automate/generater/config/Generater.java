package org.arch.framework.automate.generater.config;

import lombok.Data;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description:
 *
 *   author: lait
 *   cover: true
 *   template:
 *     dir: src/main/resources/templates
 *   project:
 *     path:
 *     name:
 *     groupId:
 *     artifactIds:
 *   modules:
 *     - artifactId:
 *       groupId:
 *       version:
 *       packages: entity,dao,service
 *       modules:
 *         - artifactId:
 *           groupId:
 *           version:
 *           packages: entity,dao,service
 *   packages:
 *     - name: entity
 *       pkg:
 *       template:
 *       suffix:
 *   database:
 *     driver: com.mysql.cj.jdbc.Driver
 *     url: "jdbc:mysql://localhost:3306/uni?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
 *     username: root
 *     password: root
 * @weixin PN15855012581
 * @date :
 */
@Data
public class Generater {
    private String author;
    private boolean cover;
    private Template template;
    private Project project;
    private List<Module> modules;
    private List<Package> packages;
    private Database database;
}
