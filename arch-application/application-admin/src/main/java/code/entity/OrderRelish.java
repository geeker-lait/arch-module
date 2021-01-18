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
 * 订单-佐料(order_relish)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("order_relish")
public class OrderRelish extends Model<OrderRelish> implements Serializable {
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
     * 订单项ID
     */
    private Long orderItemId;
    /**
     * 可以获得的积分
     */
    private Integer integration;
    /**
     * 可以活动的成长值
     */
    private Integer growth;
    /**
     * 下单时使用的积分
     */
    private Integer useIntegration;
    /**
     * 促销优化金额（促销价、满减、阶梯价）
     */
    private BigDecimal promotionAmount;
    /**
     * 积分抵扣金额
     */
    private BigDecimal integrationAmount;
    /**
     * 优惠券抵扣金额
     */
    private BigDecimal couponAmount;
    /**
     * 管理员后台调整订单使用的折扣金额
     */
    private BigDecimal discountAmount;
    /**
     * 商品sku编号
     */
    private String productSkuNo;
    /**
     * 商品促销名称
     */
    private String promotionName;
    /**
     * 该商品经过优惠后的分解金额
     */
    private BigDecimal realAmount;
    /**
     * 积分
     */
    private Integer giftIntegration;
    /**
     * 成长值
     */
    private Integer giftGrowth;
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