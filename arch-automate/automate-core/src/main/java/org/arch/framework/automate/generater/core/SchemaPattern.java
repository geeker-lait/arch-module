package org.arch.framework.automate.generater.core;

import lombok.Getter;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public enum SchemaPattern {

    API("api"),
    MVC("mvc"),
    ;
    @Getter
    private String pattern;

    SchemaPattern(String pattern) {
        this.pattern = pattern;
    }
}
