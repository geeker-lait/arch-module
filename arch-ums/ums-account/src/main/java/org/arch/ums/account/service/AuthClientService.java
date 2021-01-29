package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.entity.AuthClient;
import org.springframework.stereotype.Service;

/**
 * 授权客户端(AuthClient) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:48:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthClientService extends CrudService<AuthClient, java.lang.Long> {

}