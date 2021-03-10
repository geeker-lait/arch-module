package org.arch.ums.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.ums.enums.RelativesType;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户亲朋信息(Relatives) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:02:26
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RelativesRequest {

    /**
     * 用户亲朋信息表ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 亲朋类型: 1. 家属, 2. 朋友
     */
    private RelativesType relativesType;

    /**
     * 亲朋名称: 哥哥, 妹妹, 父亲, 母亲, 弟弟, 朋友, 同学
     */
    private String relativesName;

    /**
     * 亲朋性别: 0男, 1女
     */
    private Integer relativesSex;

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
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    private Boolean deleted;

}