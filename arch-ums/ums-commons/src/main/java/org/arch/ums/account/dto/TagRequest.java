package org.arch.ums.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号-标签(Tag) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:30:19
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TagRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long accountId;

    /**
     * 标签类目
     */
    private String tagCategory;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 标签色
     */
    private String tagColor;

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