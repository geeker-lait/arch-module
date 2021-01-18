package code.service.impl;

import code.dao.MarketNameDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.MarketNameService;
import org.springframework.stereotype.Service;

/**
 * 营销-名称服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MarketNameServiceImpl implements MarketNameService {
    private final MarketNameDao marketNameDao;

}