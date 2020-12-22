package org.arch.payment.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @since 2020-04-07
 */
@Data
@Accessors(chain = true)
@TableName("pay_channel_directive_record")
public class PayChannelDirectiveRecord{

    private static final long serialVersionUID = 1L;

    /**
     * 账号ID
     */
    private String accountId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 银行编号（ICBC）
     */
    private String bankCode;

    /**
     * 银行卡
     */
    private String bankcard;

    /**
     * 业务码
     */
    private String bizCode;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 通道码
     */
    private String channelCode;

    /**
     * 通道ID
     */
    private String channelId;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 指令码
     */
    private String directiveCode;

    /**
     * 支付ID
     */
    private String paymentId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 签约协议号
     */
    private String protocolId;

    /**
     * 返回状态码
     */
    private String returnCode;

    /**
     * 返回信息
     */
    private String returnMsg;

    /**
     * 用户ID
     */
    private String userId;


}
