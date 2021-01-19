package code.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 订单-发票(order_invoice)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("order_invoice")
public class OrderInvoice extends Model<OrderInvoice> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 订单号
     */
    private Long orderNo;
    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    private Integer invoiceType;
    /**
     * 发票抬头：个人/公司
     */
    private String invoiceTitle;
    /**
     * 发表税号
     */
    private String invoiceNo;
    /**
     * 开票金额
     */
    private BigDecimal amount;
    /**
     * 说明
     */
    private String remark;
    /**
     * 开票明细
     */
    private String invoiceItem;
    /**
     * 用户邮箱用来接受字典发票
     */
    private String userEamil;
    /**
     * 应用ID
     */
    private Long appId;
    /**
     * 店铺ID
     */
    private Long storeId;
    /**
     * 时间戳
     */
    private Date st;

}