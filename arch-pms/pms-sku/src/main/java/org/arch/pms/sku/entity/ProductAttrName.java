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
 * 产品-规格参数组下的
参数名(product_attr_name)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_attr_name")
public class ProductAttrName extends Model<ProductAttrName> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 商品分类id
     */
    private Long categoryId;
    /**
     * 商品属性规格组id
     */
    private Long specGroupId;
    /**
     * 规格组的名称
     */
    private String groupName;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * 属性的类型；0->属性，1->规格；
     */
    private Integer attrTyp;
    /**
     * 属性选择的类型:0->唯一，1->单选，2->多选
     */
    private Integer selectTyp;
    /**
     * 属性录入方式:0->手工录入，1->从列表中选取
     */
    private Integer inputTyp;
    /**
     * 分类筛选样式
     */
    private Integer filterTyp;
    /**
     * 检索类型；0->不需要进行检索；1->关键字检索；2->范围检索
     */
    private Integer searchTyp;
    /**
     * 数字类型参数的单位，非数字类型可以为空
     */
    private String unit;
    /**
     * 数值类型参数，如果需要搜索，则添加分段间隔值，如CPU频率间隔：0.5-1.0
     */
    private String segments;
    /**
     * 可选值列表(","号分割)
     */
    private String vals;
    /**
     * 是否是数字类型参数，true或false
     */
    private Integer numeric;
    /**
     * 相同属性产品是否关联；0->不关联；1->关联
     */
    private Integer related;
    /**
     * 是否支持手动新增；0->不支持；1->支持
     */
    private Integer handed;
    /**
     * 是否是sku通用属性，true或false
     */
    private Integer generic;
    /**
     * 是否用于搜索过滤，true或false
     */
    private Integer searching;
    /**
     * 排序
     */
    private Integer sorted;
    /**
     * 时间戳
     */
    private Date st;

}
