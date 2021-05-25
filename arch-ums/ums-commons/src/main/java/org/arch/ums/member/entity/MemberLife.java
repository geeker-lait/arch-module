package org.arch.ums.member.entity;

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
 * 会员生命周期(MemberLife) 实体类
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:37:53
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("member_life")
public class MemberLife extends CrudEntity<MemberLife> {
    private static final long serialVersionUID = 1L;

    /**
     * 会员生命周期ID
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 会员级别ID
     */
    @TableField(value = "`member_level_id`")
    private Long memberLevelId;

    /**
     * 会费
     */
    @TableField(value = "`member_dues`")
    private Long memberDues;

    /**
     * 会员时长, 单位: 小时
     */
    @TableField(value = "`duration`")
    private Long duration;

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