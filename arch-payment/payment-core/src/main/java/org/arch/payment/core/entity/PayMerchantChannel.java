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
@TableName("pay_merchant_channel")
public class PayMerchantChannel {

    private static final long serialVersionUID = 1L;

    /**
     * 通道码
     */
    private String channelCode;

    /**
     * 通道ID
     */
    private String channelId;

    /**
     * 商户码
     */
    private String merchantCode;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 密钥
     */
    private String secretKey;


}
