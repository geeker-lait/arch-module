package org.arch.ums.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 账号-会员账号(Member) search dto
 *
 * @author YongWu zheng
 * @date 2021-03-13 12:56:16
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MemberSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
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

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_id", this.getId(), map);
        putNoNull("EQ_account_id", this.getAccountId(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_member_level_id", this.getMemberLevelId(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_brokerage", this.getBrokerage(), map);
        putNoNull("EQ_referrer_brokerage", this.getReferrerBrokerage(), map);
        putNoNull("EQ_proposed_brokerage", this.getProposedBrokerage(), map);
        putNoNull("EQ_start_time", this.getStartTime(), map);
        putNoNull("EQ_end_time", this.getEndTime(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
