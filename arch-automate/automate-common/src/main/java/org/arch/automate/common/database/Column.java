package org.arch.automate.common.database;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 10:55 AM
 */
@Data
@Accessors(chain = true)
public class Column {
    private String name;
    private String typ;
    private String comment;
    private String length;
    /**
     * 前提条件此字段为主键且 typ = bigint/int
     */
    private boolean autoIncrement = false;
    private boolean notnull = false;
    private boolean unsigned = false;
    private boolean pk = false;
    private String def;
    private String index;
    private String unique;
    private String onUpdate;

}
