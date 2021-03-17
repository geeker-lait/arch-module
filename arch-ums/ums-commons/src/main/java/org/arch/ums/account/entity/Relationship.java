package org.arch.ums.account.entity;

import java.time.LocalDateTime;

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

/**
 * 账号-关系(Relationship) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:25:28
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_relationship")
public class Relationship extends CrudEntity<Relationship> {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 父节点ID（数据库自增）, 没有父节点则为 -1
     */
    @TableField(value = "`pid`")
    private Long pid;

    /**
     * 组
     */
    @TableField(value = "`org`")
    private Long org;

    /**
     * 深度
     */
    @TableField(value = "`deep`")
    private Long deep;

    /**
     * 顺序
     */
    @TableField(value = "`seq`")
    private Long seq;

    /**
     * 父节点顺序: 3,4,5,6(对应: deep-4, deep-3, deep-2, deep-1)
     */
    @TableField(value = "`pseq`")
    private String pseq;

    /**
     * seq 向量
     */
    @TableField(value = "`vector`")
    private String vector;

    /**
     * 推荐人ID, 没有推荐人则为 -1
     */
    @TableField(value = "`from_user_id`")
    private Long fromUserId;

    /**
     * 推荐人姓名
     */
    @TableField(value = "`from_user_name`")
    private String fromUserName;

    /**
     * 推荐人手机
     */
    @TableField(value = "`from_user_phone`")
    private String fromUserPhone;

    /**
     * 账号ID
     */
    @TableField(value = "`to_user_id`")
    private Long toUserId;

    /**
     * 用户名
     */
    @TableField(value = "`to_user_name`")
    private String toUserName;

    /**
     * 用户手机
     */
    @TableField(value = "`to_user_phone`")
    private String toUserPhone;

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
