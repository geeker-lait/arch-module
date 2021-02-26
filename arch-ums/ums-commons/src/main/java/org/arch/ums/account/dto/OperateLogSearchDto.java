package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.ums.enums.OperatorType;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 账号操作记录(OperateLog) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:08:01
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OperateLogSearchDto implements BaseSearchDto {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long accountId;

    /**
     * 操作类型(crud)
     */
    private OperatorType operatorType;

    /**
     * 操作时间
     */
    private LocalDateTime operatorTime;

    /**
     * 记录的值json
     */
    private String recordVal;

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
    public void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_id", this.getId(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_account_id", this.getAccountId(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_operator_type", this.getOperatorType(), map);
        putNoNull("EQ_operator_time", this.getOperatorTime(), map);
        putNoNull("EQ_record_val", this.getRecordVal(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_st", this.getSt(), map);
    }
}
