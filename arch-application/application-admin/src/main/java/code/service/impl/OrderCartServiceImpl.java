package code.service.impl;

import code.dao.OrderCartDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.OrderCartService;
import org.springframework.stereotype.Service;

/**
 * 订单-购物车服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderCartServiceImpl implements OrderCartService {
    private final OrderCartDao orderCartDao;

}