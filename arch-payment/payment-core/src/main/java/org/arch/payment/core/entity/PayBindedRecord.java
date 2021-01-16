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
@TableName("pay_binded_record")
public class PayBindedRecord  {

    private static final long serialVersionUID = 1L;

    /**
     * 账号ID
     */
    @TableField("account_id")
    private String accountId;

    /**
     * 银行code
     */
    @TableField("bank_code")
    private String bankCode;

    /**
     * 银行卡
     */
    @TableField("bankcard")
    private String bankcard;

    /**
     * 通道ID
     */
    @TableField("channel_id")
    private String channelId;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;


    /**
     * 签约协议号
     */
    @TableField("protocol_id")
    private String protocolId;
}
