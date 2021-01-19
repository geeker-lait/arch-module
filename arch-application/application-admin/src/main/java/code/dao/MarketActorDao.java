package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.MarketActor;
import code.mapper.MarketActorMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 营销参与者(market_actor)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class MarketActorDao extends ServiceImpl<MarketActorMapper, MarketActor> {

}