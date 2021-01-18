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
 * 订单-销售订单项(order_sale_item)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("order_sale_item")
public class OrderSaleItem extends Model<OrderSaleItem> implements Serializable {
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
     * 产品ID
     */
    private Long productId;
    /**
     * 产品图片
     */
    private String productPic;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品品牌
     */
    private String productBrand;
    /**
     * 销售价格
     */
    private BigDecimal productPrice;
    /**
     * 购买数量
     */
    private BigDecimal productQuantity;
    /**
     * 商品sku编号
     */
    private String productSkuNo;
    /**
     * 商品sku条码
     */
    private String productSkuCode;
    /**
     * 商品分类id
     */
    private Long productCategoryId;
    /**
     * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */
    private String productAttr;
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