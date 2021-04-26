package org.arch.framework.automate.xmind;

import lombok.Data;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 10:53 AM
 */
@Data
public class Table {
    private String name;
    private  String comment;
    private List<Column> columns;
    private String indexs;
    private String uniques;
    private String primaryKey;
}
