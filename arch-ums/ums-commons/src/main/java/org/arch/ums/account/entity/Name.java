package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 账号名(Name) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 22:41:13
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_name")
public class Name extends CrudEntity<Name> {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-标识 ID
     */
    @TableId(value = "`id`", type = IdType.INPUT)
    private Long id;

    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    @TableField(value = "`account_id`")
    private Long accountId;

    /**
     * 用户昵称可随机生成
     */
    @TableField(value = "`nick_name`")
    private String nickName;

    /**
     * 头像
     */
    @TableField(value = "`avatar`")
    private String avatar;

    /**
     * 来源, 推广统计用
     */
    @TableField(value = "`source`")
    private String source;

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
