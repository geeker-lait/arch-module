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
 * 支付-应用通道支持银行表(pay_channel_bank)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_channel_bank")
public class PayChannelBank extends Model<PayChannelBank> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 银行码
     */
    private String bankCode;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 通道码
     */
    private String channelCode;
    /**
     * 通道ID
     */
    private Long channelId;
    /**
     * 权重
     */
    private Integer weight;
    /**
     * 时间戳
     */
    private Date st;

}
