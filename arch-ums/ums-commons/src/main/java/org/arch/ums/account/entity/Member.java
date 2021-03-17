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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账号-会员账号(Member) 实体类
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
@TableName("account_member")
public class Member extends CrudEntity<Member> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "`id`", type = IdType.INPUT)
    private Long id;

    /**
     * 会员id
     */
    @TableField(value = "`account_id`")
    private Long accountId;

    /**
     * 会员级别ID
     */
    @TableField(value = "`member_level_id`")
    private Integer memberLevelId;

    /**
     * 总佣金
     */
    @TableField(value = "`brokerage`")
    private BigDecimal brokerage;

    /**
     * 推荐佣金
     */
    @TableField(value = "`referrer_brokerage`")
    private BigDecimal referrerBrokerage;

    /**
     * 被推荐佣金
     */
    @TableField(value = "`proposed_brokerage`")
    private BigDecimal proposedBrokerage;

    /**
     * 开始时间
     */
    @TableField(value = "`start_time`")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField(value = "`end_time`")
    private LocalDateTime endTime;

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
