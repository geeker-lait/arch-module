package org.arch.pms.sku.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.pms.sku.dao.ProductMarketingDao;
import org.arch.pms.sku.service.ProductMarketingService;
import org.springframework.stereotype.Service;

/**
 * 产品-营销服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductMarketingServiceImpl implements ProductMarketingService {
    private final ProductMarketingDao productMarketingDao;

}
