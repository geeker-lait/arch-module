package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dao.RoleMenuDao;
import org.arch.ums.account.dto.MenuSearchDto;
import org.arch.ums.account.dto.RoleMenuRequest;
import org.arch.ums.account.dto.RoleMenuSearchDto;
import org.arch.ums.account.entity.Menu;
import org.arch.ums.account.entity.RoleMenu;
import org.arch.ums.account.rest.RoleMenuRest;
import org.arch.ums.account.service.MenuService;
import org.arch.ums.account.service.RoleMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

/**
 * 账号-角色菜单(RoleMenu) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:05
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleMenuBiz implements CrudBiz<RoleMenuRequest, RoleMenu, java.lang.Long, RoleMenuSearchDto, RoleMenuSearchDto, RoleMenuService>, RoleMenuRest {

    private final TenantContextHolder tenantContextHolder;
    private final RoleMenuService roleMenuService;
    private final AuthClientBiz authClientBiz;
    private final MenuService menuService;
    private final RoleMenuDao roleMenuDao;

    @Override
    public RoleMenu resolver(TokenInfo token, RoleMenuRequest request) {
        RoleMenu roleMenu = new RoleMenu();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, roleMenu);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            roleMenu.setTenantId(token.getTenantId());
        }
        else {
            roleMenu.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return roleMenu;
    }

    @Override
    public RoleMenuService getCrudService() {
        return roleMenuService;
    }

    @Override
    public RoleMenuSearchDto getSearchDto() {
        return new RoleMenuSearchDto();
    }

    /**
     * 基于多租户, 更新角色 {@code roleId} 的 权限资源
     *
     * @param tenantId 多租户 ID
     * @param roleId   角色 ID
     * @param menuIds  权限资源 ids
     * @return 是否更新成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @NonNull
    public Boolean updateResourcesByRoleIdOfTenant(@NonNull Long tenantId,
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
     * @param scopeId  SCOPE ID
     * @param roleId   角色 ID
     * @param menuIds  权限资源 ids
     * @return 是否更新成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @NonNull
    public Boolean updateResourcesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                    @NonNull Long roleId,
                                                    @NonNull List<Long> menuIds) {
        String tenantId = tenantContextHolder.getTenantId();
        // 角色权限检查: 从 AuthClient 中根据 scopeId 获取 roleIds, 再判断是否包含此 roleId
        if (authClientBiz.hasRoleId(scopeId, roleId)) {
            return updateResourcesByRoleIdOfTenant(Long.valueOf(tenantId), roleId, menuIds);
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
    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<MenuSearchDto> findAllResourcesByRoleIdOfTenant(@NonNull Long tenantId, @NonNull Long roleId) {
        // tenantId 与 roleId 都是 long 类型, 不需要担心 sql 注入
        String subQuerySql = "SELECT `menu_id` FROM `account_role_menu` " +
                "WHERE `tenant_id` = " + tenantId +
                " AND `role_id` = " + roleId +
                " AND `deleted` = 0";
        Wrapper<Menu> queryWrapper = Wrappers.lambdaQuery(Menu.class)
                                             .eq(Menu::getTenantId, tenantId)
                                             .eq(Menu::getDeleted, Boolean.FALSE)
                                             .inSql(Menu::getId, subQuerySql);

        List<Menu> menuList = this.menuService.findAllBySpec(queryWrapper);
        return menuList.stream()
                       .map(menu -> {
                           MenuSearchDto menuSearchDto = new MenuSearchDto();
                           BeanUtils.copyProperties(menu, menuSearchDto);
                           return menuSearchDto;
                       })
                       .collect(toList());
    }

    /**
     * 基于 SCOPE, 查询指定角色 {@code roleId} 的权限资源列表
     *
     * @param scopeId  SCOPE ID
     * @param roleId   角色 ID
     * @return 权限资源列表
     */
    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<MenuSearchDto> findAllResourcesByRoleIdOfScopeId(@NonNull Long scopeId,
                                                                 @NonNull Long roleId) {

        String tenantId = tenantContextHolder.getTenantId();
        // 角色权限检查: 从 AuthClient 中根据 scopeId 获取 roleIds, 再判断是否包含此 roleId
        if (authClientBiz.hasRoleId(scopeId, roleId)) {
            return findAllResourcesByRoleIdOfTenant(Long.valueOf(tenantId), roleId);
        }
        throw new BusinessException(AuthStatusCode.FORBIDDEN);
    }

}
