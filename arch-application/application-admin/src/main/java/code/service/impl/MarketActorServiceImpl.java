package code.service.impl;

import code.dao.MarketActorDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.MarketActorService;
import org.springframework.stereotype.Service;

/**
 * 营销参与者服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MarketActorServiceImpl implements MarketActorService {
    private final MarketActorDao marketActorDao;

}