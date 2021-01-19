package org.arch.pms.sku.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.pms.sku.dao.ProductAttrGroupDao;
import org.arch.pms.sku.service.ProductAttrGroupService;
import org.springframework.stereotype.Service;

/**
 * 产品-规格组，规格参数的分组表，
每个商品分类下有多个规格参数组服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductAttrGroupServiceImpl implements ProductAttrGroupService {
    private final ProductAttrGroupDao productAttrGroupDao;

}
