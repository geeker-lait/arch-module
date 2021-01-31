package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.account.mapper.AuthClientMapper;
import org.arch.ums.account.vo.AuthClientVo;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;

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
public class AuthClientDao extends ServiceImpl<AuthClientMapper, AuthClient> implements CrudDao<AuthClient> {

    private final AuthClientMapper authClientMapper;
    private final TenantContextHolder tenantContextHolder;

    /**
     * 根据 clientId 与 clientSecret 查询 scopes
     * @param clientId      client id
     * @param clientSecret  client secret
     * @return  返回 scopes 字符串, 可能为 null.
     */
    @Nullable
    public String getScopesByClientIdAndClientSecret(String clientId, String clientSecret) {
        Integer tenantId = Integer.valueOf(tenantContextHolder.getTenantId());
        return authClientMapper.getScopesByClientIdAndClientSecret(tenantId, clientId, clientSecret);
    }

    /**
     * 获取所有租户的 scopes
     *
     * @return scopes
     */
    public List<AuthClientVo> getAllScopes() {
        return authClientMapper.getAllScopes();
    }
}