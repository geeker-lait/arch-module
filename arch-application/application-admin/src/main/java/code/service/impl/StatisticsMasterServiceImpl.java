package code.service.impl;

import code.dao.StatisticsMasterDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.StatisticsMasterService;
import org.springframework.stereotype.Service;

/**
 * 统计-行为统计服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class StatisticsMasterServiceImpl implements StatisticsMasterService {
    private final StatisticsMasterDao statisticsMasterDao;

}