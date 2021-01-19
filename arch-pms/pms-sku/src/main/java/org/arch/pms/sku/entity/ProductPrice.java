package org.arch.pms.sku.entity;

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
 * 产品-价格(针对批发)(product_price)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_price")
public class ProductPrice extends Model<ProductPrice> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 产品id
     */
    private String spuId;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 折后价格
     */
    private BigDecimal price;
    /**
     * 时间戳
     */
    private Date st;

}
