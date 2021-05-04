package org.arch.framework.automate.xmind.table;

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
     * 前提条件 pk = false
     */
    private boolean autoIncrement = false;
    private boolean notNull = false;
    private boolean unsigned = false;
    private String def;
    private String onUpdate;

}
