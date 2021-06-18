package org.arch.ums.member.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会员权益(MemberRights) request
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:39:06
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MemberRightsRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会员权益ID
     */
    private Long id;

    /**
     * 会员级别ID
     */
    private Long memberLevelId;

    /**
     * 权益类型, 默认: 0 为基础权益
     */
    private Integer rightsTyp;

    /**
     * 权益名称
     */
    private String rightsName;

    /**
     * 权益值
     */
    private Integer rightsValue;

    /**
     * 权益码
     */
    private String rightsCode;

    /**
     * 租户 id
     */
    private Integer tenantId;

    /**
     * 应用 id
     */
    @JsonIgnore
    private Integer appId;

    /**
     * 店铺 id
     */
    @JsonIgnore
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    @JsonIgnore
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
