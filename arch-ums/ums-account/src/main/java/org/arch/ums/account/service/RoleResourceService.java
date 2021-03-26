package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.RoleResourceDao;
import org.arch.ums.account.entity.RoleResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 账号-角色资源表(RoleResource) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:43
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleResourceService extends CrudService<RoleResource, java.lang.Long> {
    private final RoleResourceDao roleResourceDao;

    /**
     * 获取所有租户的所有角色资源权限
     * @return  Map(tenantAuthority, Map(role, map(uri/path, Set(permission))), 如果不存在这返回空集合.
     */
    @NonNull
    @Transactional(readOnly = true)
    public Map<String, Map<String, Map<String, Set<String>>>> listAllResourceAuthorities() {
        // TODO: 2021.3.26
        return new HashMap<>(0);
    }

    /**
     * 多租户获取指定角色指定资源的信息
     *
     * @param tenantId      多租户 ID
     * @param roleId        用户的角色 Id
     * @param resourceIds   用户的资源 ids
     * @return  Map(tenantAuthority, Map(role, map(uri/path, Set(permission))), 如果不存在这返回空集合.
     */
    @NonNull
    @Transactional(readOnly = true)
    public Map<String, Map<String, Map<String, Set<String>>>> findAuthoritiesByRoleIdOfTenant(
            @NonNull Integer tenantId,
            @NonNull Long roleId,
            @NonNull List<Long> resourceIds) {
        // TODO: 2021.3.26
        return new HashMap<>(0);
    }
}
