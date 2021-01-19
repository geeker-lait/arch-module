package code.service.impl;

import code.dao.OrderSaleItemDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.OrderSaleItemService;
import org.springframework.stereotype.Service;

/**
 * 订单-销售订单项服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderSaleItemServiceImpl implements OrderSaleItemService {
    private final OrderSaleItemDao orderSaleItemDao;

}