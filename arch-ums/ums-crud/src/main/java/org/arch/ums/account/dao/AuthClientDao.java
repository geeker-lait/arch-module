package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.account.mapper.AuthClientMapper;
import org.springframework.stereotype.Repository;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

/**
 * 授权客户端(AuthClient) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:47:57
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class AuthClientDao extends CrudServiceImpl<AuthClientMapper, AuthClient> implements CrudDao<AuthClient> {

    private final AuthClientMapper authClientMapper;
    private final TenantContextHolder tenantContextHolder;

}