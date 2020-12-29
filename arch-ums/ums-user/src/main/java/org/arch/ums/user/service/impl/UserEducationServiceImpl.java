package org.arch.ums.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.dao.UserEducationDao;
import org.arch.ums.service.UserEducationService;
import org.springframework.stereotype.Service;

/**
 * 用户学历信息服务接口实现
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserEducationServiceImpl implements UserEducationService {
    private final UserEducationDao userEducationDao;

}