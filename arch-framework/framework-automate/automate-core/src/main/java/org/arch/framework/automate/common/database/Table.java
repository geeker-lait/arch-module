package org.arch.framework.automate.common.database;

import lombok.Data;
import lombok.experimental.Accessors;
import org.arch.framework.automate.generater.core.Metadata;

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
public class Table implements Metadata {
    private String name;
    private String comment;
    /**
     * 主键语句, 不包含末尾逗号
     */
    private String pkStatement;
    /**
     * 索引语句, 不包含末尾逗号
     */
    private final List<String> uniquesStatements = new ArrayList<>();
    /**
     * 唯一索引语句, 不包含末尾逗号
     */
    private final List<String> indexesStatements = new ArrayList<>();
    private final List<Column> columns = new ArrayList<>();
}
