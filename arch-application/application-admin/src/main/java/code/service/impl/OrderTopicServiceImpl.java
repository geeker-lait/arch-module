package code.service.impl;

import code.dao.OrderTopicDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.OrderTopicService;
import org.springframework.stereotype.Service;

/**
 * 订单-评价服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderTopicServiceImpl implements OrderTopicService {
    private final OrderTopicDao orderTopicDao;

}