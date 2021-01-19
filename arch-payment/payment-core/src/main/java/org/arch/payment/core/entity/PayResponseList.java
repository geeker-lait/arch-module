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
 * 支付-响应记录(pay_response_list)实体类
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_response_list")
public class PayResponseList extends Model<PayResponseList> implements Serializable {
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
     * 时间戳
     */
    private Date st;

}
