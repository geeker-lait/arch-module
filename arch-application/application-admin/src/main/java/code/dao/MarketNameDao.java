package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.MarketName;
import code.mapper.MarketNameMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 营销-名称(market_name)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class MarketNameDao extends ServiceImpl<MarketNameMapper, MarketName> {

}