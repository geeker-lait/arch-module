package org.arch.workflow.ums.response;

import org.arch.workflow.common.model.ObjectMap;
import org.arch.workflow.ums.domain.User;

/**
 * 人员数据转换类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年3月28日
 */
class UserConverter {

    static ObjectMap convertAuth(User user, String token) {
        ObjectMap result = new ObjectMap();
        result.put("id", user.getId());
        result.put("name", user.getName());
        result.put("avatar", user.getAvatar());
        result.put("token", token);
        return result;
    }

}
