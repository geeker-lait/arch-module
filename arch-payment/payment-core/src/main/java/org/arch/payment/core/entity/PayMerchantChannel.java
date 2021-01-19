package org.arch.payment.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付-商户通道(pay_merchant_channel)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_merchant_channel")
public class PayMerchantChannel extends Model<PayMerchantChannel> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 通道ID
     */
    private Long channelId;
    /**
     * 商户ID
     */
    private Long merchantId;
    /**
     * 商户号
     */
    private Long merchantNo;
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
    /**
     * 时间戳
     */
    private Date st;

}
