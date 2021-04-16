package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.RoleMenuDao;
import org.arch.ums.account.entity.Menu;
import org.arch.ums.account.entity.RoleMenu;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.util.stream.Collectors.toList;

/**
 * 账号-角色菜单(RoleMenu) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:42
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleMenuService extends CrudService<RoleMenu, java.lang.Long> {

    private final RoleMenuDao roleMenuDao;
    private final MenuService menuService;
    private final AuthClientService authClientService;

    /**
     * 基于多租户, 更新角色 {@code roleId} 的 权限资源
     *
     * @param tenantId 多租户 ID
     * @param roleId   角色 ID
     * @param menuIds  权限资源 ids
     * @return 是否更新成功
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @NonNull
    public boolean updateResourcesByRoleIdOfTenant(@NonNull Long tenantId,
                                                   @NonNull Long roleId,
                                                   @NonNull List<Long> menuIds) {
        List<RoleMenu> roleMenuList =
                menuIds.stream()
                       .map(menuId -> new RoleMenu().setMenuId(menuId)
                                                    .setRoleId(roleId)
                                                    .setTenantId(tenantId.intValue())
                                                    .setDeleted(FALSE))
                       .collect(toList());

        Wrapper<RoleMenu> roleMenuWrapper = Wrappers.lambdaQuery(RoleMenu.class)
                                                    .eq(RoleMenu::getTenantId, tenantId)
                                                    .eq(RoleMenu::getRoleId, roleId)
                                                    .eq(RoleMenu::getDeleted, FALSE);
        boolean removeResult = roleMenuDao.remove(roleMenuWrapper);
        if (!removeResult) {
            return false;
        }
        return roleMenuDao.saveBatch(roleMenuList);
    }

    /**
     * 基于 SCOPE, 更新角色 {@code roleId} 的 权限资源
     *
     * @param tenantId 多租户 ID
     * @param scopeId  SCOPE ID
     * @param roleId   角色 ID
     * @param menuIds  权限资源 ids
     * @return 是否更新成功
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @NonNull
    public boolean updateResourcesByRoleIdOfScopeId(@NonNull Long tenantId,
                                                    @NonNull Long scopeId,
                                                    @NonNull Long roleId,
                                                    @NonNull List<Long> menuIds) {
        // 角色权限检查: 从 AuthClient 中根据 scopeId 获取 roleIds, 再判断是否包含此 roleId
        if (authClientService.hasRoleId(scopeId, roleId)) {
            return updateResourcesByRoleIdOfTenant(tenantId, roleId, menuIds);
        }
        throw new BusinessException(AuthStatusCode.FORBIDDEN);
    }

    /**
     * 基于多租户, 查询指定角色 {@code roleId} 的权限资源列表
     *
     * @param tenantId 多租户 ID
     * @param roleId   角色 ID
     * @return 权限资源列表
     */
    @Transactional(readOnly = true)
    @NonNull
    public List<Menu> findAllResourcesByRoleIdOfTenant(@NonNull Long tenantId, @NonNull Long roleId) {
        // tenantId 与 roleId 都是 long 类型, 不需要担心 sql 注入
        String subQuerySql = "SELECT `menu_id` FROM `account_role_menu` " +
                                "WHERE `tenant_id` = " + tenantId +
                                " AND `role_id` = " + roleId +
                                " AND `deleted` = 0";
        Wrapper<Menu> queryWrapper = Wrappers.lambdaQuery(Menu.class)
                                             .eq(Menu::getTenantId, tenantId)
                                             .eq(Menu::getDeleted, Boolean.FALSE)
                                             .inSql(Menu::getId, subQuerySql);

        return this.menuService.findAllBySpec(queryWrapper);
    }

    /**
     * 基于 SCOPE, 查询指定角色 {@code roleId} 的权限资源列表
     * @param tenantId  多租户 ID
     * @param scopeId   SCOPE ID
     * @param roleId    角色 ID
     * @return  权限资源列表
     */
    @Transactional(readOnly = true)
    @NonNull
    public List<Menu> findAllResourcesByRoleIdOfScopeId(@NonNull Long tenantId,
                                                        @NonNull Long scopeId,
                                                        @NonNull Long roleId) {
        // 角色权限检查: 从 AuthClient 中根据 scopeId 获取 roleIds, 再判断是否包含此 roleId
        if (authClientService.hasRoleId(scopeId, roleId)) {
            return findAllResourcesByRoleIdOfTenant(tenantId, roleId);
        }
        throw new BusinessException(AuthStatusCode.FORBIDDEN);
    }
}
