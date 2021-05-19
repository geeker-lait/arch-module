package org.arch.ums.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 会员生命周期(MemberLife) search dto
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:37:53
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MemberLifeSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
    private static final long serialVersionUID = 1L;
    /**
     * 会员生命周期ID
     */
    private Long id;

    /**
     * 会员级别ID
     */
    private Long memberLevelId;

    /**
     * 会费
     */
    private Long memberDues;

    /**
     * 会员时长, 单位: 小时
     */
    private Long duration;

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
        putNoNull("EQ_id", this.getId(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_member_level_id", this.getMemberLevelId(), map);
        putNoNull("EQ_member_dues", this.getMemberDues(), map);
        putNoNull("EQ_duration", this.getDuration(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
    }
}
