package org.arch.pms.sku.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 产品-分类(product_category)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_category")
public class ProductCategory extends Model<ProductCategory> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 父节点
     */
    private String pid;
    /**
     * 类目图标
     */
    private String icon;
    /**
     * 类目名
     */
    private String categoryName;
    /**
     * 类目码
     */
    private String categoryCode;
    /**
     * 类目类型
     */
    private Long categoryTyp;
    /**
     * 产品数量
     */
    private String productCount;
    /**
     * 是否显示在导航栏：0->不显示；1->显示
     */
    private Integer navStatus;
    /**
     * 关键字
     */
    private Long keywords;
    /**
     * 分类级别：0->1级；1->2级
     */
    private String level;
    /**
     * 描述
     */
    private String descr;
    /**
     * 自定义属性时控制该产品属性的数量
     */
    private String attrsCount;
    /**
     * 自定属性时控制该产品分类下属性参数的数量
     */
    private String paramsCount;
    /**
     * 单位
     */
    private String productUnit;

}
