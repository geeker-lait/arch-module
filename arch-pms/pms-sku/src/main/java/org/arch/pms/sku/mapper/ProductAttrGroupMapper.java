package org.arch.pms.sku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.pms.sku.entity.ProductAttrGroup;

/**
 * 产品-规格组，规格参数的分组表，
每个商品分类下有多个规格参数组(product_attr_group)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface ProductAttrGroupMapper extends BaseMapper<ProductAttrGroup> {

}
