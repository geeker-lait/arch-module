package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;
import org.arch.framework.ums.enums.ResourceType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号-资源(Resource) 实体类
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:22:45
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_resource")
public class Resource extends CrudEntity<Resource> {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-资源表ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号-资源类目ID
     */
    private Long categoryId;

    /**
     * 资源名
     */
    private String resourceName;

    /**
     * 资源码
     */
    private String resourceCode;

    /**
     * 类型: 1目录, 2菜单, 3按钮, 4链接
     */
    private ResourceType resourceType;

    /**
     * 资源值
     */
    private String resourceVal;

    /**
     * 资源路径
     */
    private String resourcePath;

    /**
     * 资源图标
     */
    private String resourceIcon;

    /**
     * 资源描述
     */
    private String resourceDesc;

    /**
     * 是否隐藏: 0不隐藏, 1隐藏. 默认: 0
     */
    private Integer visible;

    /**
     * 层级
     */
    private Integer level;

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