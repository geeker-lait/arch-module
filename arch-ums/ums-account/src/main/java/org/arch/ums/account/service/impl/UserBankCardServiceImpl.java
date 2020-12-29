package org.arch.ums.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.dao.UserBankCardDao;
import org.arch.ums.service.UserBankCardService;
import org.springframework.stereotype.Service;

/**
 * 用户银行卡信息服务接口实现
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserBankCardServiceImpl implements UserBankCardService {
    private final UserBankCardDao userBankCardDao;

}