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
 * 支付-通道(pay_channel)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_channel")
public class PayChannel extends Model<PayChannel> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 通道名称
     */
    private String channelName;
    /**
     * 通道码
     */
    private String channelCode;
    /**
     * 通道连接
     */
    private String channelUrl;
    /**
     * 描述说明
     */
    private String descr;
    /**
     * 排序
     */
    private Integer sotrted;
    /**
     * 时间戳
     */
    private Date st;

}
