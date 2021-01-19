package code.service.impl;

import code.dao.OrderSwapperDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.OrderSwapperService;
import org.springframework.stereotype.Service;

/**
 * 订单-收发货方信息服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderSwapperServiceImpl implements OrderSwapperService {
    private final OrderSwapperDao orderSwapperDao;

}