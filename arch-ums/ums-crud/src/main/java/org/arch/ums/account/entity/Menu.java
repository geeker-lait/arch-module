package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号-菜单(Menu) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:25:27
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_menu")
public class Menu extends CrudEntity<Menu> {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-菜单ID
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 父节点ID, 如果没有父节点则为: -1
     */
    @TableField(value = "`pid`")
    private Long pid;

    /**
     * 英文码
     */
    @TableField(value = "`menu_code`")
    private String menuCode;

    /**
     * 菜单名称
     */
    @TableField(value = "`menu_name`")
    private String menuName;

    /**
     * 菜单值
     */
    @TableField(value = "`menu_val`")
    private String menuVal;

    /**
     * 层级
     */
    @TableField(value = "`level`")
    private Integer level;

    /**
     * 排序
     */
    @TableField(value = "`sorted`")
    private Integer sorted;

    /**
     * 是否iframe: 1是, 0不是, 默认: 1
     */
    @TableField(value = "`frame`")
    private Integer frame;

    /**
     * 图标
     */
    @TableField(value = "`icon`")
    private String icon;

    /**
     * 租户 id
     */
    @TableField(value = "`tenant_id`")
    private Integer tenantId;

    /**
     * 应用 id
     */
    @TableField(value = "`app_id`")
    private Integer appId;

    /**
     * 店铺 id
     */
    @TableField(value = "`store_id`")
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    @TableField(value = "`rev`")
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    @TableField(value = "`dt`")
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    @TableField(value = "`deleted`")
    private Boolean deleted;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
