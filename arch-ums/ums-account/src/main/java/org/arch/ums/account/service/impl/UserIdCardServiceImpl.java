package org.arch.ums.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.dao.UserIdCardDao;
import org.arch.ums.service.UserIdCardService;
import org.springframework.stereotype.Service;

/**
 * 用户身份证表服务接口实现
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserIdCardServiceImpl implements UserIdCardService {
    private final UserIdCardDao userIdCardDao;

}