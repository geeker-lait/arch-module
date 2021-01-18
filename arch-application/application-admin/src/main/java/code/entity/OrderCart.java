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
 * 订单-购物车(order_cart)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("order_cart")
public class OrderCart extends Model<OrderCart> implements Serializable {
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
     * 商品分类
     */
    private Long productCategoryId;
    /**
     * 产品品牌
     */
    private String productBrand;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品副标题（卖点）
     */
    private String productSubTitle;
    /**
     * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */
    private String productAttrs;
    /**
     * 商品主图
     */
    private String productPic;
    /**
     * 产品sn
     */
    private String productSn;
    /**
     * 产品skuNo
     */
    private String productSkuNo;
    /**
     * 商品sku条码
     */
    private String productSkuCode;
    /**
     * 购买数量
     */
    private BigDecimal quantity;
    /**
     * 添加到购物车的价格
     */
    private BigDecimal price;
    /**
     * 状态（下单之后对应商品就不应该显示在购物车了）
     */
    private Integer status;
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