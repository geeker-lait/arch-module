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
 * 支付-状态码映射(pay_status_code)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_status_code")
public class PayStatusCode extends Model<PayStatusCode> implements Serializable {
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
     * 通道码
     */
    private String channelCode;
    /**
     * 通道状态码
     */
    private String channelStateCode;
    /**
     * 通过状态码描述
     */
    private String channelStateDescr;
    /**
     * 统一状态码
     */
    private String stateCode;
    /**
     * 统一状态码描述
     */
    private String stateDescr;
    /**
     * 时间戳
     */
    private Date st;

}
