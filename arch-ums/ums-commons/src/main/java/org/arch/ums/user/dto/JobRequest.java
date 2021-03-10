package org.arch.ums.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户工作信息(Job) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:08:08
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class JobRequest {

    /**
     * 用户工作信息表ID
     */
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

}