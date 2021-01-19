package org.arch.pms.sku.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.pms.sku.dao.ProductRelishDao;
import org.arch.pms.sku.service.ProductRelishService;
import org.springframework.stereotype.Service;

/**
 * 产品-佐料服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductRelishServiceImpl implements ProductRelishService {
    private final ProductRelishDao productRelishDao;

}
