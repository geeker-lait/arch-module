package org.arch.framework.automate.generater.core;

import lombok.Getter;

/**
 * @author lait.zhang@gmail.com
 * @description: 模板名称
 * @weixin PN15855012581
 * @date 2/9/2021 6:03 PM
 */
public enum TemplateName {
    ENTITY("entity.ftl"),
    DAO("dao.ftl"),
    MAPPER("mapper.ftl"),
    SERVICE("service.ftl"),
    BIZ("biz.ftl"),
    CONTROLLER("controller.ftl"),
    SEARCH("search.ftl"),
    DTO("dto.ftl"),
    POM("pom.ftl"),
    DDL("ddl.ftl"),
    YML("yml.ftl"),
    APPLICATION("application.ftl"),
    ;
    @Getter
    private String template;

    TemplateName(String template) {
        this.template = template;
    }
}
