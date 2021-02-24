package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.automate.api.DirectiveRequest;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-02-20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DirectiveRequestDto implements DirectiveRequest {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 数据库名称
     */
    private String databaseName;

}
