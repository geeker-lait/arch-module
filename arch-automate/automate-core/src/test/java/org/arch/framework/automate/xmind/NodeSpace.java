package org.arch.framework.automate.xmind;

import lombok.Getter;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 10:41 AM
 */
public enum NodeSpace {

    MODULE("module"),
    ENTITY("entity"),
    LONG("long"),
    INTEGER("integer"),
    STRING("string"),
    BOOLEAN("boolean"),
    DATE("date")
    ;

    @Getter
    private String name;

    NodeSpace(String name) {
        this.name = name;
    }
}
