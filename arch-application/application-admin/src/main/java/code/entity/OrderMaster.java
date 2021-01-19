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
 * 订单-订单主体(order_master)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("order_master")
public class OrderMaster extends Model<OrderMaster> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 卖方账号ID
     */
    private Long sellerAccountId;
    /**
     * 卖方账号名称
     */
    private String sellerAccountName;
    /**
     * 买方账号ID
     */
    private Long buyerAccountId;
    /**
     * 买方账号名称
     */
    private String buyerAccountName;
    /**
     * 订单号
     */
    private Long orderNo;
    /**
     * 订单码
     */
    private String orderCode;
    /**
     * 订单类型：销售订单；秒杀订单，采购订单，用表名来表示......
     */
    private Integer orderTyp;
    /**
     * 订单来源：0->PC订单；1->app订单
     */
    private Integer orderSource;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
     */
    private Integer orderStatus;
    /**
     * 下单时间
     */
    private Date orderTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 发货时间
     */
    private Date deliveryTime;
    /**
     * 确认收货时间
     */
    private Date receiveTime;
    /**
     * 自动确认时间
     */
    private Date confirmTime;
    /**
     * 应付金额（实际支付金额）
     */
    private BigDecimal payAmount;
    /**
     * 支付方式：0->未支付；1->支付宝；2->微信
     */
    private Integer payTyp;
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