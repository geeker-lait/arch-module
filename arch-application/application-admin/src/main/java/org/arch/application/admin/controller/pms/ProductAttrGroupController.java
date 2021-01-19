package org.arch.application.admin.controller.pms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.pms.sku.service.ProductAttrGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品-规格组，规格参数的分组表，
 * 每个商品分类下有多个规格参数组服务控制器
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/productAttrGroup")
public class ProductAttrGroupController {
    private final ProductAttrGroupService productAttrGroupService;

}
