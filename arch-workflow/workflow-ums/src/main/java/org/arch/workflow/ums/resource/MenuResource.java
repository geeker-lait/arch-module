package org.arch.workflow.ums.resource;

import org.arch.workflow.common.jpa.Criteria;
import org.arch.workflow.common.jpa.Restrictions;
import org.arch.workflow.common.resource.BaseResource;
import org.arch.workflow.common.resource.PageResponse;
import org.arch.workflow.common.utils.ObjectUtils;
import org.arch.workflow.ums.constant.ErrorConstant;
import org.arch.workflow.ums.constant.TableConstant;
import org.arch.workflow.ums.domain.Menu;
import org.arch.workflow.ums.domain.Role;
import org.arch.workflow.ums.repository.MenuRepository;
import org.arch.workflow.ums.repository.RoleMenuRepository;
import org.arch.workflow.ums.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 菜单资源控制类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月8日
 */
@RestController
public class MenuResource extends BaseResource {
    private final MenuRepository menuRepository;
    private final RoleRepository roleRepository;
    private final RoleMenuRepository roleMenuRepository;

    @Autowired
    public MenuResource(MenuRepository menuRepository, RoleRepository roleRepository, RoleMenuRepository roleMenuRepository) {
        this.menuRepository = menuRepository;
        this.roleRepository = roleRepository;
        this.roleMenuRepository = roleMenuRepository;
    }

    private Menu getMenuFromRequest(Integer id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        if (menu == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.MENU_NOT_FOUND);
        }
        return menu;
    }

    @GetMapping(value = "/menus")
    @ResponseStatus(value = HttpStatus.OK)
    public PageResponse getMenus(@RequestParam Map<String, String> requestParams) {
        Criteria<Menu> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("id", requestParams.get("id")));
        criteria.add(Restrictions.eq("parentId", requestParams.get("parentId")));
        criteria.add(Restrictions.eq("status", requestParams.get("status")));
        criteria.add(Restrictions.like("name", requestParams.get("name")));
        criteria.add(Restrictions.like("tenantId", requestParams.get("tenantId")));
        return createPageResponse(menuRepository.findAll(criteria, getPageable(requestParams)));
    }

    @GetMapping(value = "/menus/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Menu getMenu(@PathVariable Integer id) {
        return getMenuFromRequest(id);
    }

    @PostMapping("/menus")
    @ResponseStatus(HttpStatus.CREATED)
    public Menu createMenu(@RequestBody Menu menuRequest) {
        return menuRepository.save(menuRequest);
    }

    @PutMapping(value = "/menus/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Menu updateMenu(@PathVariable Integer id, @RequestBody Menu menuRequest) {
        Menu menu = getMenuFromRequest(id);
        menu.setName(menuRequest.getName());
        menu.setStatus(menuRequest.getStatus());
        menu.setIcon(menuRequest.getIcon());
        menu.setOrder(menuRequest.getOrder());
        menu.setParentId(menuRequest.getParentId());
        menu.setType(menuRequest.getType());
        menu.setRoute(menuRequest.getRoute());
        menu.setRemark(menuRequest.getRemark());
        menu.setTenantId(menuRequest.getTenantId());
        return menuRepository.save(menu);
    }

    @PutMapping(value = "/menus/{id}/switch")
    @ResponseStatus(value = HttpStatus.OK)
    public Menu switchStatus(@PathVariable Integer id) {
        Menu menu = getMenuFromRequest(id);
        if (menu.getStatus() == TableConstant.MENU_STATUS_NORMAL) {
            menu.setStatus(TableConstant.MENU_STATUS_STOP);
        } else {
            menu.setStatus(TableConstant.MENU_STATUS_NORMAL);
        }
        return menuRepository.save(menu);
    }

    @GetMapping(value = "/menus/{id}/roles")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Role> getMenuRoles(@PathVariable Integer id) {
        return roleRepository.findByMenuId(id);
    }

    @DeleteMapping(value = "/menus/{id}/roles/{roleId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteMenuRole(@PathVariable Integer id, @PathVariable(value = "roleId") Integer roleId) {
        roleMenuRepository.deleteByMenuIdAndRoleId(id, roleId);
    }

    @DeleteMapping(value = "/menus/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable Integer id) {
        Menu menu = getMenuFromRequest(id);
        if (menu.getType() == TableConstant.MENU_TYPE_PARENT) {
            List<Menu> children = menuRepository.findByParentId(menu.getId());
            if (ObjectUtils.isNotEmpty(children)) {
                exceptionFactory.throwForbidden(ErrorConstant.MENU_HAVE_CHILDREN);
            }
        } else {
            List<Role> roles = roleRepository.findByMenuId(menu.getId());
            if (ObjectUtils.isNotEmpty(roles)) {
                exceptionFactory.throwForbidden(ErrorConstant.MENU_ALREADY_ROLE_USE, roles.get(0).getName());
            }
        }
        menuRepository.delete(menu);
    }
}
