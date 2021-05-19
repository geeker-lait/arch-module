package org.arch.ums.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.ums.enums.OperatorType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号操作记录(OperateLog) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:08:01
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OperateLogRequest implements Serializable {
    private static final long serialVersionUID = 1L;

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