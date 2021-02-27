package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: tables:
 * - name:
 *   comment:
 *   pk:
 *   indexs:
 *   columns:
 *      - name:
 *        typ:
 *        length:
 * @weixin PN15855012581
 * @date 2/17/2021 8:35 PM
 */
@Data
@NoArgsConstructor
@ToString
public class TableProperties {
    // 表名
    private String name;
    // 说明
    private String comment;
    // 主键
    private String pk;
    // 外键 fk1,fk2,fk3...
    private String fks;
    // 唯一键 uni1,uni2,uni3...
    private String uniques;
    // 索引字段 indx1,indx2,indx3...
    private String indexs;
    @NestedConfigurationProperty
    private List<ColumnsProperties> columns;
}
