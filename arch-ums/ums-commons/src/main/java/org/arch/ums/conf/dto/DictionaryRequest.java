package org.arch.ums.conf.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 数据字典(Dictionary) request
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:00:39
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DictionaryRequest {

    /**
     * id
     */
    private Long id;

    /**
     * 业务码
     */
    @NotBlank(message = "业务码不能为空")
    private String code;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 备注
     */
    private String mark;

    /**
     * 租户 id
     */
    private Integer tenantId;

    /**
     * 应用 id
     */
    private Integer appId;

    /**
     * 店铺 id
     */
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    private Boolean deleted;

}
