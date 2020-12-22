package org.arch.payment.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @since 2020-04-08
 */
@Data
@Accessors(chain = true)
@TableName("pay_channel_bank")
public class PayChannelBank  {

    private static final long serialVersionUID = 1L;

    /**
     * 支持银行码
     */
    @TableField("bank_code")
    private String bankCode;

    /**
     * 支持银行名称
     */
    @TableField("bank_name")
    private String bankName;

    /**
     * 支付通道码
     */
    @TableField("channel_code")
    private String channelCode;

    /**
     * 支付通道ID
     */
    @TableField("channel_id")
    private String channelId;

    /**
     * 权重
     */
    @TableField("weight")
    private Integer weight;


}
