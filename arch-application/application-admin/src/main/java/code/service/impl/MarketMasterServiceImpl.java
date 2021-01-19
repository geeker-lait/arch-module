package code.service.impl;

import code.dao.MarketMasterDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.MarketMasterService;
import org.springframework.stereotype.Service;

/**
 * 营销主题服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MarketMasterServiceImpl implements MarketMasterService {
    private final MarketMasterDao marketMasterDao;

}