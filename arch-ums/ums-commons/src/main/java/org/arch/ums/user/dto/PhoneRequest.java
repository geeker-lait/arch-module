package org.arch.ums.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.ums.enums.Mno;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户电话信息(Phone) request
 *
 * @author YongWu zheng
 * @date 2021-03-17 22:19:44
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PhoneRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户电话信息表ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 运营商: 移动/电信/联通/电话..
     */
    private Mno mno;

    /**
     * 顺序
     */
    private Integer sorted;

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
