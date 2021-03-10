package org.arch.ums.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户工作信息(Job) 实体类
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:07:55
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
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 公司行业
     */
    private String groupChannel;

    /**
     * 公司名称
     */
    private String groupName;

    /**
     * 职位名称
     */
    private String postName;

    /**
     * 职级
     */
    private String postLevel;

    /**
     * 顺序
     */
    private Integer sorted;

    /**
     * 入职时间
     */
    private LocalDate hiredateTime;

    /**
     * 离职时间
     */
    private LocalDate dimissionTime;

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
    private LocalDateTime dt;

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