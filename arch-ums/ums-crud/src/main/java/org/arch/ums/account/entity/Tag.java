package org.arch.ums.account.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;

/**
 * 账号-标签(Tag) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:25:30
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_tag")
public class Tag extends CrudEntity<Tag> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "`account_id`")
    private Long accountId;

    /**
     * 标签类目
     */
    @TableField(value = "`tag_category`")
    private String tagCategory;

    /**
     * 标签名
     */
    @TableField(value = "`tag_name`")
    private String tagName;

    /**
     * 标签色
     */
    @TableField(value = "`tag_color`")
    private String tagColor;

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