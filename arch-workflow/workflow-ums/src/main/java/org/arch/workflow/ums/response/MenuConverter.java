package org.arch.workflow.ums.response;

import org.arch.workflow.common.model.ObjectMap;
import org.arch.workflow.common.utils.ObjectUtils;
import org.arch.workflow.ums.constant.TableConstant;
import org.arch.workflow.ums.domain.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单数据转换类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年3月28日
 */
class MenuConverter {

    static List<ObjectMap> convertMultiSelect(List<Menu> menus, List<Menu> roleMenus) {
        List<ObjectMap> menuList = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getType() == TableConstant.MENU_TYPE_CHILD) {
                continue;
            }
            List<ObjectMap> children = new ArrayList<>();
            for (Menu childMenu : menus) {
                if (menu.getId().equals(childMenu.getParentId())) {
                    if (ObjectUtils.isNotEmpty(roleMenus) && roleMenus.contains(childMenu)) {
                        children.add(ObjectMap.of("id", childMenu.getId(), "name", childMenu.getName(), "selected", true));
                    } else {
                        children.add(ObjectMap.of("id", childMenu.getId(), "name", childMenu.getName(), "selected", false));
                    }
                }
            }
            if (ObjectUtils.isNotEmpty(children)) {
                menuList.add(ObjectMap.of("id", menu.getId(), "name", menu.getName(), "group", true));
                menuList.addAll(children);
                menuList.add(ObjectMap.of("group", false));
            }
        }
        return menuList;
    }

    static List<ObjectMap> convertUserMenus(List<Menu> parentMenus, List<Menu> childMenus) {
        List<ObjectMap> menuList = new ArrayList<>();
        for (Menu menu : parentMenus) {
            List<ObjectMap> childList = new ArrayList<>();
            for (Menu childMenu : childMenus) {
                if (menu.getId().equals(childMenu.getParentId())) {
                    childList.add(convertMenuMap(childMenu));
                }
            }
            if (ObjectUtils.isNotEmpty(childList)) {
                ObjectMap menuMap = convertMenuMap(menu);
                menuMap.put("children", childList);
                menuList.add(menuMap);
            }
        }
        return menuList;
    }

    private static ObjectMap convertMenuMap(Menu menu) {
        ObjectMap objectMap = new ObjectMap();
        objectMap.put("id", menu.getId());
        objectMap.put("name", menu.getName());
        objectMap.put("path", menu.getRoute());
        objectMap.put("icon", menu.getIcon());
        return objectMap;
    }

}
