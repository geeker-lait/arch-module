package org.arch.framework.automate.xmind.nodespace;

/**
 * Prams property
 *
 * @author YongWu zheng
 * @version V2.0  Created by 2021-04-28 15:04
 */
public enum PramProperty {
    LONG("Long"),
    STRING("String"),
    INT("Integer"),
    INTEGER("Integer"),
    VOID("void"),
    BOOLEAN("Boolean"),
    FLOAT("Float"),
    DOUBLE("Double"),
    BYTE("Byte"),
    SHORT("Short"),
    DATE("Date");

    /**
     * 类型
     */
    private final String type;

    PramProperty(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}