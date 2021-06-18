package org.arch.ums.user.entity;

import java.time.LocalDate;
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
 * 用户工作信息(Job) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:30:11
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_job")
public class Job extends CrudEntity<Job> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户工作信息表ID
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 公司行业
     */
    @TableField(value = "`group_channel`")
    private String groupChannel;

    /**
     * 公司名称
     */
    @TableField(value = "`group_name`")
    private String groupName;

    /**
     * 职位名称
     */
    @TableField(value = "`post_name`")
    private String postName;

    /**
     * 职级
     */
    @TableField(value = "`post_level`")
    private String postLevel;

    /**
     * 顺序
     */
    @TableField(value = "`sorted`")
    private Integer sorted;

    /**
     * 入职时间
     */
    @TableField(value = "`hiredate_time`")
    private LocalDate hiredateTime;

    /**
     * 离职时间
     */
    @TableField(value = "`dimission_time`")
    private LocalDate dimissionTime;

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
