package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;
import org.arch.framework.ums.enums.OperatorType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号操作记录(OperateLog) 实体类
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:07:49
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_operate_log")
public class OperateLog extends CrudEntity<OperateLog> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
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


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}