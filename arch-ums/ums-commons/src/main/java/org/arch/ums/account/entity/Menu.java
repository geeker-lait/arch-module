package org.arch.ums.account.entity;

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
 * @date 2021-01-29 21:14:14
 * @since 1.0.0
 */
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
    @TableId("id")
    private Long id;

    /**
     * 父节点ID
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