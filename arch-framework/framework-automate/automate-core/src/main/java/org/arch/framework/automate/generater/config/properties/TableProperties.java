package org.arch.framework.automate.generater.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
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
public class TableProperties {
    private String name;
    private String comment;
    private String pk;
    private String indexs;
    @NestedConfigurationProperty
    private List<ColumnsProperties> columns;
}
