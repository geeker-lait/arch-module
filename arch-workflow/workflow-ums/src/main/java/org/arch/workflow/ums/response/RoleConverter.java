package org.arch.workflow.ums.response;

import org.arch.workflow.common.model.ObjectMap;
import org.arch.workflow.common.utils.ObjectUtils;
import org.arch.workflow.ums.domain.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色数据转换类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年3月28日
 */
class RoleConverter {

    static List<ObjectMap> convertMultiSelect(List<Role> roles, List<Role> userRoles) {
        List<ObjectMap> menuList = new ArrayList<>();
        for (Role role : roles) {
            if (ObjectUtils.isNotEmpty(userRoles) && userRoles.contains(role)) {
                menuList.add(ObjectMap.of("id", role.getId(), "name", role.getName(), "selected", true));
            } else {
                menuList.add(ObjectMap.of("id", role.getId(), "name", role.getName(), "selected", false));
            }
        }
        return menuList;
    }

}
