package org.arch.pms.sku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.pms.sku.entity.ProductPrice;

/**
 * 产品-价格(针对批发)(product_price)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface ProductPriceMapper extends BaseMapper<ProductPrice> {

}
