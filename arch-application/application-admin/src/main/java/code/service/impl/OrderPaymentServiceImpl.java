package code.service.impl;

import code.dao.OrderPaymentDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.OrderPaymentService;
import org.springframework.stereotype.Service;

/**
 * 订单-支付记录表服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {
    private final OrderPaymentDao orderPaymentDao;

}