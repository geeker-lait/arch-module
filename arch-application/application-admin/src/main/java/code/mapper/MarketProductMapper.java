package code.mapper;

import code.entity.MarketProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 营销关联产品(market_product)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface MarketProductMapper extends BaseMapper<MarketProduct> {

}