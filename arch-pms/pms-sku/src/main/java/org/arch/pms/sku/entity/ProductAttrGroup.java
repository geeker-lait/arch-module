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
 * 产品-规格组，规格参数的分组表，
每个商品分类下有多个规格参数组(product_attr_group)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_attr_group")
public class ProductAttrGroup extends Model<ProductAttrGroup> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 商品分类id，一个分类下有多个规格组
     */
    private Long categoryId;
    /**
     * 属性规格组的名称
     */
    private String groupName;
    /**
     * 排序
     */
    private Integer sorted;
    /**
     * 时间戳
     */
    private Date st;

}
