package org.arch.application.admin.controller.oms;

import code.service.OrderSaleItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 订单-销售订单项服务控制器
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orderSaleItem")
public class OrderSaleItemController{
    private final OrderSaleItemService orderSaleItemService;

}
