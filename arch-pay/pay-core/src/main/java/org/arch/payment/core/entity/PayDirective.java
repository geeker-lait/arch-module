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
 * 支付-指令集(pay_directive)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_directive")
public class PayDirective extends Model<PayDirective> implements Serializable {
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
     * 指令名称
     */
    private String directiveName;
    /**
     * 指令码
     */
    private String directiveCode;
    /**
     * 指令URI
     */
    private String directiveUri;
    /**
     * 定向URL
     */
    private String redirectUrl;
    /**
     * 指令回调URL
     */
    private String callbackUrl;
    /**
     * 权重
     */
    private Integer weight;
    /**
     * 描述
     */
    private String descr;
    /**
     * 时间戳
     */
    private Date st;

}
