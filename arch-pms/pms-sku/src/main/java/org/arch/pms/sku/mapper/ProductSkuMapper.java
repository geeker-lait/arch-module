package org.arch.pms.sku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.pms.sku.entity.ProductSku;

/**
 * 产品-SKU(product_sku)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

}