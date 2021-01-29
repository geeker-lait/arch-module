package org.arch.ums.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户学历信息(Education) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:04:35
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class EducationRequest {

    /**
     * 用户学历信息表ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 证书编码
     */
    private String certificateNo;

    /**
     * 证书名称
     */
    private String certificateName;

    /**
     * 证书登记机构
     */
    private String certificateOrg;

    /**
     * 学历(如: 大专, 本科, 硕士, 博士)
     */
    private String certificateLevel;

    /**
     * 顺序
     */
    private Integer sorted;

    /**
     * 颁发时间
     */
    private LocalDate awardtime;

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

}