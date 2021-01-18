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
 * 产品-SPU(product_spu)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_spu")
public class ProductSpu extends Model<ProductSpu> implements Serializable {
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
     * 产品id，分布式ID生成，全局唯一
     */
    private String spuId;
    /**
     * 产品code
     */
    private String spuCode;
    /**
     * 名称
     */
    private String spuName;
    /**
     * 产品主图相册：[{url1:"",sorted:1},{url2:"",sorted:2}]
     */
    private String spuAlbumJson;
    /**
     * 属性组集合(spu共用)：{groupName1:[{"key":"k1","value":"v1",sorted:1},{"key":"k2","value":"v2",sorted:2}],groupName2:[{"key":"k1","value":"v1",sorted:1},{"key":"k2","value":"v2",sorted:2}]}
     */
    private String spuAttrJson;
    /**
     * 产品类目id
     */
    private String categoryId;
    /**
     * 产品类目名称
     */
    private Long categoryName;
    /**
     * 品牌id
     */
    private Integer brandId;
    /**
     * 品牌名称
     */
    private Long brandName;
    /**
     * 售价
     */
    private BigDecimal sale;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存，所有sku 可售库存之和
     */
    private Integer stock;
    /**
     * 最低库存
     */
    private String lowStock;
    /**
     * 单位
     */
    private String unit;
    /**
     * 标题
     */
    private String title;
    /**
     * 子标题
     */
    private String subTitle;
    /**
     * 关键字
     */
    private String keywords;
    /**
     * 详情头
     */
    private String detailTitle;
    /**
     * 详情页
     */
    private Integer detailHtml;
    /**
     * 手机详情页
     */
    private Long detailMobileHtml;
    /**
     * 可用状态
     */
    private Integer usable;
    /**
     * 发布状态
     */
    private Integer published;
    /**
     * 最新状态
     */
    private Integer latest;
    /**
     * 推荐状态；0->不推荐；1->推荐
     */
    private Integer recommand;
    /**
     * 审核状态：0->未审核；1->审核通过
     */
    private Integer verify;
    /**
     * 是否为预告商品：0->不是；1->是
     */
    private Integer presell;
    /**
     * 售后服务ID，多个用“，”分割
     */
    private String serviceIds;
    /**
     * 运费模板ID
     */
    private String freightId;
    /**
     * 排序
     */
    private Integer sorted;
    /**
     * 时间戳
     */
    private Date st;

}
