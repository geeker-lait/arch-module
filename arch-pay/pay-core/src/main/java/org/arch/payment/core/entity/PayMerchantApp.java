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
 * 支付-商户应用(pay_merchant_app)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_merchant_app")
public class PayMerchantApp extends Model<PayMerchantApp> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 系统账号ID
     */
    private Long accountId;
    /**
     * 系统为其生成商户号
     */
    private Long merchantNo;
    /**
     * 应用ID
     */
    private Long appId;
    /**
     * 应用码
     */
    private String appCode;
    /**
     * 应用名
     */
    private String appName;
    /**
     * 应用业务类型，适配不同支付通道，可从字典获取
     */
    private Integer appTyp;
    /**
     * 应用下载链接
     */
    private String appUrl;
    /**
     * 时间戳
     */
    private Date st;

}
