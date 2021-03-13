package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账号-会员账号(Member) request
 *
 * @author YongWu zheng
 * @date 2021-03-13 12:56:16
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MemberRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 会员id
     */
    private Long accountId;

    /**
     * 会员级别ID
     */
    private Integer memberLevelId;

    /**
     * 总佣金
     */
    private BigDecimal brokerage;

    /**
     * 推荐佣金
     */
    private BigDecimal referrerBrokerage;

    /**
     * 被推荐佣金
     */
    private BigDecimal proposedBrokerage;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

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
