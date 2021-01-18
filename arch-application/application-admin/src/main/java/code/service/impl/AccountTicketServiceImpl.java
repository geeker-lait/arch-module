package code.service.impl;

import code.dao.AccountTicketDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountTicketService;
import org.springframework.stereotype.Service;

/**
 * 账号-券服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountTicketServiceImpl implements AccountTicketService {
    private final AccountTicketDao accountTicketDao;

}