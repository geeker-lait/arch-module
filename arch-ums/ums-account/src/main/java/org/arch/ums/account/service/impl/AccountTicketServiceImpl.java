package org.arch.ums.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dao.AccountTicketDao;
import org.arch.ums.account.service.AccountTicketService;
import org.springframework.stereotype.Service;

/**
 * 账号-券服务接口实现
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountTicketServiceImpl implements AccountTicketService {
    private final AccountTicketDao accountTicketDao;

}