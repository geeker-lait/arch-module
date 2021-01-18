package code.service.impl;

import code.dao.OrderMasterDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.OrderMasterService;
import org.springframework.stereotype.Service;

/**
 * 订单-订单主体服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderMasterServiceImpl implements OrderMasterService {
    private final OrderMasterDao orderMasterDao;

}