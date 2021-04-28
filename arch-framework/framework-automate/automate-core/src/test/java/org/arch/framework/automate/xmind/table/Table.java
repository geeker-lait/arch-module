package org.arch.framework.automate.xmind.table;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 10:53 AM
 */
@Data
@Accessors(chain = true)
public class Table {
    private String name;
    private String comment;
    private final List<Column> columns = new ArrayList<>();
    private String indexes;
    private String uniques;
    private String primaryKey;
}
