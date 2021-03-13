package org.arch.framework.automate.generater.core;

import lombok.Getter;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public enum SchemaReader {

    API("api"),
    MVC("mvc"),
    ;
    @Getter
    private String reader;

    SchemaReader(String reader) {
        this.reader = reader;
    }
}
