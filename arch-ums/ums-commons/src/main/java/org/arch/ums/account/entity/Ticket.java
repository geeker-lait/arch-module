package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;
import org.arch.framework.ums.enums.TicketType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号-券(Ticket) 实体类
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:30:49
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_ticket")
public class Ticket extends CrudEntity<Ticket> {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-券ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    private LocalDateTime dt;

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