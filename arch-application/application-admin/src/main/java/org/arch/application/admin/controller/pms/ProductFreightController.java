package org.arch.application.admin.controller.pms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.pms.sku.service.ProductFreightService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品-费用模板服务控制器
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/productFreight")
public class ProductFreightController {
    private final ProductFreightService productFreightService;

}
