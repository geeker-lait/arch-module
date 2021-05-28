package org.arch.automate.common.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.arch.automate.common.Metadata;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 10:53 AM
 */
@Data
@EqualsAndHashCode
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
    private final Set<String> uniquesStatements = new HashSet<>();
    /**
     * 唯一索引语句, 不包含末尾逗号
     */
    private final Set<String> indexesStatements = new HashSet<>();
    private final Set<Column> columns = new HashSet<>();
}
