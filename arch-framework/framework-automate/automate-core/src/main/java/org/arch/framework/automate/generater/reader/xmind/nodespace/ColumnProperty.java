package org.arch.framework.automate.generater.reader.xmind.nodespace;

/**
 * database column property
 *
 * @author YongWu zheng
 * @version V2.0  Created by 2021-04-28 15:03
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum ColumnProperty {
    LENGTH,
    PK,
    UNIQUE,
    NOTNULL,
    DEFAULT,
    UNSIGNED,
    ON_UPDATE,
    AUTO_INCREMENT,
    INDEX,
    /**
     * 等效于 LENGTH
     */
    LEN,
    /**
     * 等效于 DEFAULT
     */
    DEF,
    /**
     * 等效于 INDEX
     */
    IDX,
    /**
     * 等效于 UNIQUE
     */
    UNQ,
    /**
     * 等效于 AUTO_INCREMENT
     */
    INCR,

}