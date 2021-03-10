package org.arch.framework.automate.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表单schema(FormSchema) request
 *
 * @author lait
 * @date 2021-02-10 15:55:49
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormSchemaRequest {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 库名称/项目名称
     */
    private String schemaName;

    /**
     * 库名称/项目名称的code
     */
    private String schemaCode;

    /**
     * 描述
     */
    private String descr;

    /**
     * 时间戳
     */
    private LocalDateTime dt;

}
