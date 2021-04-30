package org.arch.framework.automate.xmind.nodespace;

/**
 * api Param property
 *
 * @author YongWu zheng
 * @version V2.0  Created by 2021-04-28 15:03
 */
public enum ParamProperty {
    ARRAY_TYP("arrayTyp"),
    LIST_TYP("listTyp"),
    SET_TYP("setTyp"),
    MAP_TYP("mapTyp"),
    COLLECTION_TYP("collectionTyp");

    private final String type;

    ParamProperty(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}