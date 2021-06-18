package org.arch.form.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目业务(FormBiz) request
 *
 * @author lait
 * @date 2021-02-10 15:55:42
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormBizRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 业务名称
     */
    private String bizName;

    /**
     * 业务码
     */
    private String bizCode;

    /**
     * 业务说明
     */
    private String descr;

    /**
     * 排序
     */
    private Integer sorted;

    /**
     * 是否逻辑删除
     */
    private Boolean deleted;

    /**
     * 时间戳
     */
    private LocalDateTime dt;

}