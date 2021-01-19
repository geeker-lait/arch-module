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
 * 产品-费用模板(product_freight)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_freight")
public class ProductFreight extends Model<ProductFreight> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 店铺ID
     */
    private String storeId;
    /**
     * 品类ID
     */
    private String categoryId;
    /**
     * 运费模板ID,分布式ID生成
     */
    private String freightId;
    /**
     * 运费模板名称
     */
    private String freightName;
    /**
     * 费用
     */
    private BigDecimal fee;
    /**
     * 物流Id
     */
    private String logisticsId;
    /**
     * 物流名称
     */
    private String logisticsName;
    /**
     * 物流CODE
     */
    private String logisticsCode;
    /**
     * 排序
     */
    private Integer sorted;
    /**
     * 是否删除
     */
    private Integer deleted;
    /**
     * 时间戳
     */
    private Date st;

}
