package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.StatisticsMaster;
import code.mapper.StatisticsMasterMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 统计-行为统计(statistics_master)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class StatisticsMasterDao extends ServiceImpl<StatisticsMasterMapper, StatisticsMaster> {

}