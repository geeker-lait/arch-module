package org.arch.pms.sku.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 产品-SKU(product_sku)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_sku")
public class ProductSku extends Model<ProductSku> implements Serializable {
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
     * sku编码
     */
    private Long skuCode;
    /**
     * 价格
     */
    private Long price;
    /**
     * 处理或促销价格
     */
    private Long disposePrice;
    /**
     * 图片：[{url1:"",sorted:1},{url2:"",sorted:2}]
     */
    private Long picJson;
    /**
     * 销量
     */
    private Long sale;
    /**
     * 库存
     */
    private Long stock;
    /**
     * 最低库存
     */
    private Long lowStock;
    /**
     * 锁定库存
     */
    private Long lockStock;
    /**
     * 规格组集合（sku专用）：{groupName1:[{"key":"spec-k1","value":"spec-v1"，sorted:1}]，groupName2:[{"key":"spec-k2","value":"spec-v2"，sorted:2}]}
     */
    private String specJson;
    /**
     * 时间戳
     */
    private Date st;

}
