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
 * 支付-商户(pay_merchant)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_merchant")
public class PayMerchant extends Model<PayMerchant> implements Serializable {
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
     * 公司名称
     */
    private String merchantName;
    /**
     * 公司营业执照编码
     */
    private String merchantCode;
    /**
     * 联系人
     */
    private String concatName;
    /**
     * 联系人手机
     */
    private String concatPhone;
    /**
     * 时间戳
     */
    private Date st;

}
