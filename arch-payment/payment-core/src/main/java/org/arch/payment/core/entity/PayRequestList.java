package org.arch.payment.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付-请求记录(pay_request_list)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_request_list")
public class PayRequestList extends Model<PayRequestList> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 应用ID
     */
    private Long appId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 银行卡
     */
    private String bankcard;
    /**
     * 银行码
     */
    private String bankCode;
    /**
     * 业务码
     */
    private String bizCode;
    /**
     * 业务id
     */
    private Long bizId;
    /**
     * 通道码
     */
    private String channelCode;
    /**
     * 通道id
     */
    private Long channelId;
    /**
     * 商户id
     */
    private Long merchantId;
    /**
     * 设备ID
     */
    private String deviceId;
    /**
     * 指令码
     */
    private String directiveCode;
    /**
     * 三方需要的支付流水号
     */
    private Long paymentId;
    /**
     * 手机号
     */
    private String phoneNum;
    /**
     * 协议号
     */
    private String protocolId;
    /**
     * 返回码
     */
    private String returnCode;
    /**
     * 返回信息
     */
    private String returnMsg;
    /**
     * 时间戳
     */
    private Date st;

}
