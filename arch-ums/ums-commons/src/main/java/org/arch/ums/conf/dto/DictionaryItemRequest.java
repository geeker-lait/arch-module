package org.arch.ums.conf.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据字典明细(DictionaryItem) request
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:17:59
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DictionaryItemRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * conf_dictionary id
     */
    @NotNull(message = "dictionary id 不能为 null")
    private Long dictionaryId;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 标题对应的值
     */
    private String val;

    /**
     * title 顺序
     */
    @NotNull(message = "seq 不能为 null")
    private Integer seq;

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
    @JsonIgnore
    private Integer appId;

    /**
     * 店铺 id
     */
    @JsonIgnore
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    @JsonIgnore
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
