package org.arch.pms.sku.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.pms.sku.dao.ProductAttrNameDao;
import org.arch.pms.sku.service.ProductAttrNameService;
import org.springframework.stereotype.Service;

/**
 * 产品-规格参数组下的
参数名服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductAttrNameServiceImpl implements ProductAttrNameService {
    private final ProductAttrNameDao productAttrNameDao;

}
