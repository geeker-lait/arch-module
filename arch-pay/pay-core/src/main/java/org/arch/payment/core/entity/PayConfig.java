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
 * 支付-配置(pay_config)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_config")
public class PayConfig extends Model<PayConfig> implements Serializable {
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
     * 配置key
     */
    private String configKey;
    /**
     * 配置key对应的json串
     */
    private String configJson;
    /**
     * 描述
     */
    private String descr;
    /**
     * 时间戳
     */
    private Date st;

}
