package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账号-岗位(Post) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:19:16
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PostRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 父id
     */
    private Long postPid;

    /**
     * 岗位名
     */
    private String postName;

    /**
     * 岗位code
     */
    private String postCode;

    /**
     * icon
     */
    private String postIcon;

    /**
     * 薪资
     */
    private BigDecimal salary;

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
    private LocalDateTime st;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    private Boolean deleted;

}