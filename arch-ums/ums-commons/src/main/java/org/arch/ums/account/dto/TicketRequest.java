package org.arch.ums.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.ums.enums.TicketType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号-券(Ticket) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:31:01
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TicketRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-券ID
     */
    private Long id;

    /**
     * 用户id
     */
    private Long accountId;

    /**
     * 券类型：打折，优惠，抵用....
     */
    private Integer ticketType;

    /**
     * 券号
     */
    private String ticketNo;

    /**
     * 券值
     */
    private String ticketVal;

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