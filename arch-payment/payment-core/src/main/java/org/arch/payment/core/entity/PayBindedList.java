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
 * 支付-绑卡记录(pay_binded_list)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_binded_list")
public class PayBindedList extends Model<PayBindedList> implements Serializable {
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
     * 通道ID
     */
    private Long channelId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 身份证号
     */
    private String idcard;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 银行卡
     */
    private String bankcard;
    /**
     * 卡码
     */
    private String bankCode;
    /**
     * 预留手机号
     */
    private String phone;
    /**
     * 协议id
     */
    private String protocolId;
    /**
     * 时间戳
     */
    private Date st;

}
