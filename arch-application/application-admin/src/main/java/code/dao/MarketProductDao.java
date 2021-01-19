package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.MarketProduct;
import code.mapper.MarketProductMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 营销关联产品(market_product)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class MarketProductDao extends ServiceImpl<MarketProductMapper, MarketProduct> {

}