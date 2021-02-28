package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 账号-组织机构(Group) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:01:50
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GroupSearchDto implements BaseSearchDto {

    /**
     * 账号-权限ID
     */
    private Long id;

    /**
     * 父ID
     */
    private Long groupPid;

    /**
     * 组code
     */
    private String groupCode;

    /**
     * 组织架构名
     */
    private String groupName;

    /**
     * 组织架构ICON
     */
    private String groupIcon;

    /**
     * 排序
     */
    private Integer sorted;

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

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_id", this.getId(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_group_pid", this.getGroupPid(), map);
        putNoNull("EQ_sorted", this.getSorted(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_group_code", this.getGroupCode(), map);
        putNoNull("EQ_group_name", this.getGroupName(), map);
        putNoNull("EQ_group_icon", this.getGroupIcon(), map);
        putNoNull("EQ_st", this.getSt(), map);
    }
}
