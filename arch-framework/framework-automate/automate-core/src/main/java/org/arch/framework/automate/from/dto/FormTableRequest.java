package org.arch.framework.automate.from.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 业务表单(FormTable) request
 *
 * @author lait
 * @date 2021-02-10 15:45:29
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormTableRequest {

    /**
     * id主键
     */
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 业务id
     */
    private Long bizId;

    /**
     * 表单实力id
     */
    private Long tableInstanceId;

    /**
     * 是否逻辑删除
     */
    private Boolean deleted;

    /**
     * 时间戳
     */
    private LocalDateTime dt;

}
