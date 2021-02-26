package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.OauthTokenDao;
import org.arch.ums.account.entity.OauthToken;
import org.springframework.stereotype.Service;

/**
 * 第三方账号授权(OauthToken) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:25:18
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OauthTokenService extends CrudService<OauthToken, java.lang.Long> {
    private final OauthTokenDao oauthTokenDao = (OauthTokenDao) crudDao;

}
