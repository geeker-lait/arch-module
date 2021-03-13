package org.arch.framework.automate.generater.core;

import lombok.Getter;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:27 AM
 */
public enum SourceName {
    DATABASE_SOURCE("database"),
    EXCEl_SOURCE("excel"),
    JSON_SOURCE("json"),
    XMIND_SOURCE("xmind"),
    ;
    @Getter
    private String source;
    SourceName(String source) {
        this.source = source;
    }
}
