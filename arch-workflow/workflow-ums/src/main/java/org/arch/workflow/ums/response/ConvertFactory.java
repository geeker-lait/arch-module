package org.arch.workflow.ums.response;

import org.arch.workflow.common.model.ObjectMap;
import org.arch.workflow.ums.domain.Group;
import org.arch.workflow.ums.domain.Menu;
import org.arch.workflow.ums.domain.Role;
import org.arch.workflow.ums.domain.User;

import java.util.List;

/**
 * 数据返回转换工厂类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年3月28日
 */
public class ConvertFactory {
    public static ObjectMap convertUseAuth(User user, String token) {
        return UserConverter.convertAuth(user, token);
    }

    public static List<ObjectMap> convertUserGroups(List<Group> groups, List<Group> roleGroups) {
        return GroupConverter.convertMultiSelect(groups, roleGroups);
    }

    public static List<ObjectMap> convertUseRoles(List<Role> roles, List<Role> userRoles) {
        return RoleConverter.convertMultiSelect(roles, userRoles);
    }

    public static List<ObjectMap> convertRoleMenus(List<Menu> menus, List<Menu> roleMenus) {
        return MenuConverter.convertMultiSelect(menus, roleMenus);
    }

    public static List<ObjectMap> convertUserMenus(List<Menu> parentMenus, List<Menu> childMenus) {
        return MenuConverter.convertUserMenus(parentMenus, childMenus);
    }
}
