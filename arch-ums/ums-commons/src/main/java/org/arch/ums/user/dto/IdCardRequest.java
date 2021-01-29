package org.arch.ums.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户身份证表(IdCard) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:06:03
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class IdCardRequest {

    /**
     * 用户身份证表ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 名字
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 民族
     */
    private String nation;

    /**
     * 居住地
     */
    private String domicile;

    /**
     * 颁发机构
     */
    private String signOrg;

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