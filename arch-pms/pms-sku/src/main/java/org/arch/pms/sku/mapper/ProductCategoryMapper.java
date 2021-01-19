package org.arch.pms.sku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.pms.sku.entity.ProductCategory;

/**
 * 产品-分类(product_category)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

}
