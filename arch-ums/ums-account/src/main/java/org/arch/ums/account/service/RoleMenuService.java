package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.RoleMenuDao;
import org.arch.ums.account.entity.RoleMenu;
import org.arch.ums.account.vo.MenuVo;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * 获取所有租户的所有角色的菜单权限
     * @return 所拥有的所有菜单权限集合,
     * Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], Set(Menu[sorted])))), 中括号中的排序字段.
     */
    @NonNull
    @Transactional(readOnly = true)
    public Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> listAllMenuOfAllTenant() {
        // TODO: 2021.3.26
        return new HashMap<>(0);
    }

    /**
     * 多租户获取指定角色指定菜单的信息, 只包含带有 uri 与 permission(menu_vul) 的菜单权限
     *
     * @param tenantId  多租户 ID
     * @param roleId    用户的角色 Id
     * @param menuIds   用户的菜单 ids
     * @return 所拥有的所有菜单权限集合,
     * Map(tenantAuthority, Map(roleAuthority, Map(Menu[level,sorted], Set(Menu[sorted])))), 中括号中的排序字段.
     */
    @NonNull
    @Transactional(readOnly = true)
    public Map<String, Map<String, Map<MenuVo, Set<MenuVo>>>> findMenuByRoleOfTenant(
                                                                @PathVariable(value = "tenantId") Integer tenantId,
                                                                @PathVariable(value = "roleId") Long roleId,
                                                                @RequestBody List<Long> menuIds) {
        // TODO: 2021.3.26
        return new HashMap<>(0);
    }
}
