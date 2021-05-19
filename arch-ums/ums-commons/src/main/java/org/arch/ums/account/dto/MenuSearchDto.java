package org.arch.ums.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 账号-菜单(Menu) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:15:47
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MenuSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
    private static final long serialVersionUID = 1L;
    /**
     * 账号-菜单ID
     */
    private Long id;

    /**
     * 父节点ID, 如果没有父节点则为: -1
     */
    private Long pid;

    /**
     * 英文码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单值
     */
    private String menuVal;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sorted;

    /**
     * 是否iframe: 1是, 0不是, 默认: 1
     */
    private Integer frame;

    /**
     * 图标
     */
    private String icon;

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
        putNoNull("EQ_pid", this.getPid(), map);
        putNoNull("EQ_sorted", this.getSorted(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_menu_code", this.getMenuCode(), map);
        putNoNull("EQ_menu_name", this.getMenuName(), map);
        putNoNull("EQ_menu_val", this.getMenuVal(), map);
        putNoNull("EQ_level", this.getLevel(), map);
        putNoNull("EQ_frame", this.getFrame(), map);
        putNoNull("EQ_icon", this.getIcon(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
