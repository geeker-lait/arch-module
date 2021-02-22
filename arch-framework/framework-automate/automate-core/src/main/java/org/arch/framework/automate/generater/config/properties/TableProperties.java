package org.arch.framework.automate.generater.config.properties;

import lombok.Data;

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
public class TableProperties {
    private String name;
    private String comment;
    private String pk;
    private String indexs;
    private List<ColumnsProperties> columns;
}
