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
 * 支付-应用商户通道策略(pay_strategy)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_strategy")
public class PayStrategy extends Model<PayStrategy> implements Serializable {
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
     * 应用码
     */
    private String appCode;
    /**
     * 通道ID
     */
    private Long channelId;
    /**
     * 商户ID
     */
    private Long merchantId;
    /**
     * 权重或优先级
     */
    private Integer priority;
    /**
     * 规则ID,这里后期借助规则引擎来实现，预留规则id,前期先简单静态，根据权重或优先级
     */
    private Long ruleId;
    /**
     * 时间戳
     */
    private Date st;

}
