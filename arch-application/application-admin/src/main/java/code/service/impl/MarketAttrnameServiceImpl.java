package code.service.impl;

import code.dao.MarketAttrnameDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.MarketAttrnameService;
import org.springframework.stereotype.Service;

/**
 * 营销-自定义动态属性服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MarketAttrnameServiceImpl implements MarketAttrnameService {
    private final MarketAttrnameDao marketAttrnameDao;

}