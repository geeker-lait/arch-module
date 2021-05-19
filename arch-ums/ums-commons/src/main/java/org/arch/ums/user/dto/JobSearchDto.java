package org.arch.ums.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户工作信息(Job) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:08:08
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class JobSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
    /**
     * 用户工作信息表ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 公司行业
     */
    private String groupChannel;

    /**
     * 公司名称
     */
    private String groupName;

    /**
     * 职位名称
     */
    private String postName;

    /**
     * 职级
     */
    private String postLevel;

    /**
     * 顺序
     */
    private Integer sorted;

    /**
     * 入职时间
     */
    private LocalDate hiredateTime;

    /**
     * 离职时间
     */
    private LocalDate dimissionTime;

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
        putNoNull("EQ_user_id", this.getUserId(), map);
        putNoNull("EQ_sorted", this.getSorted(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_group_channel", this.getGroupChannel(), map);
        putNoNull("EQ_group_name", this.getGroupName(), map);
        putNoNull("EQ_post_name", this.getPostName(), map);
        putNoNull("EQ_post_level", this.getPostLevel(), map);
        putNoNull("EQ_hiredate_time", this.getHiredateTime(), map);
        putNoNull("EQ_dimission_time", this.getDimissionTime(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
