package org.arch.ums.account.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.ums.enums.TicketType;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 账号-券(Ticket) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:31:02
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
public class TicketSearchDto extends BaseSearchDto {

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
    private TicketType ticketType;

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

    @Override
    protected void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_account_id", this.getAccountId(), map);
        putNoNull("EQ_ticket_type", this.getTicketType(), map);
        putNoNull("EQ_ticket_no", this.getTicketNo(), map);
        putNoNull("EQ_ticket_val", this.getTicketVal(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_st", this.getSt(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
    }
}
