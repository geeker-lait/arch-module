package org.arch.ums.account.entity;

import java.math.BigDecimal;
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
 * 账号-岗位(Post) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:25:28
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_post")
public class Post extends CrudEntity<Post> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 父id
     */
    @TableField(value = "`post_pid`")
    private Long postPid;

    /**
     * 岗位名
     */
    @TableField(value = "`post_name`")
    private String postName;

    /**
     * 岗位code
     */
    @TableField(value = "`post_code`")
    private String postCode;

    /**
     * icon
     */
    @TableField(value = "`post_icon`")
    private String postIcon;

    /**
     * 薪资
     */
    @TableField(value = "`salary`")
    private BigDecimal salary;

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
