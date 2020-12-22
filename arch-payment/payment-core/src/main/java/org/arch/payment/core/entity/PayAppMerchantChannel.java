package org.arch.payment.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
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
@TableName("pay_app_merchant_channel")
public class PayAppMerchantChannel {

    private static final long serialVersionUID = 1L;

    /**
     * 通道ID
     */
    @TableField("channel_id")
    private String channelId;

    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private String merchantId;

    @TableField("weight")
    private Integer weight;


}
