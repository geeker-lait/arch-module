package code.service.impl;

import code.dao.MarketProductDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.MarketProductService;
import org.springframework.stereotype.Service;

/**
 * 营销关联产品服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MarketProductServiceImpl implements MarketProductService {
    private final MarketProductDao marketProductDao;

}