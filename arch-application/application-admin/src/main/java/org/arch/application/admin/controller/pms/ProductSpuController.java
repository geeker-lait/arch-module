package org.arch.application.admin.controller.pms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.pms.sku.service.ProductSpuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品-SPU服务控制器
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/productSpu")
public class ProductSpuController {
    private final ProductSpuService productSpuService;

}
