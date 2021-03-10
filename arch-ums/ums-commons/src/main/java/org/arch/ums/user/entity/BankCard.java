package org.arch.ums.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户银行卡信息(BankCard) 实体类
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:03:36
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_bank_card")
public class BankCard extends CrudEntity<BankCard> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户银行卡信息表ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 银行卡号
     */
    private String cardNo;

    /**
     * 卡bin 如: 6221, 6222
     */
    private String cardBin;

    /**
     * 卡code 如: ICBC, ABC
     */
    private String cardCode;

    /**
     * 卡cvv
     */
    private String cvv;

    /**
     * 卡类型:0: 储蓄卡, 1: 借记卡
     */
    private String cardType;

    /**
     * 基于user_id的顺序
     */
    private Integer sorted;

    /**
     * 有效期
     */
    private LocalDate validity;

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