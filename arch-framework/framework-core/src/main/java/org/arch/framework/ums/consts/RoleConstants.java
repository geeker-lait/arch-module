package org.arch.framework.ums.consts;

import top.dcenter.ums.security.common.consts.RbacConstants;

/**
 * 角色常量
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.14 23:14
 */
public interface RoleConstants {
    /**
     * 角色前缀
     */
    String ROLE_PREFIX = RbacConstants.DEFAULT_ROLE_PREFIX;
    /**
     * 租户前缀
     */
    String TENANT_PREFIX = RbacConstants.DEFAULT_ROLE_PREFIX;
    /**
     * 权限分隔符
     */
    String AUTHORITY_SEPARATOR = RbacConstants.DATABASE_AUTHORITY_DELIMITER;

    /**
     * 角色组权限前缀
     */
    String GROUP_PREFIX = RbacConstants.DEFAULT_GROUP_PREFIX;
    /**
     * 资源权限前缀
     */
    String SCOPE_PREFIX = RbacConstants.DEFAULT_SCOPE_PREFIX;

    /**
     * 权限分隔符
     */
    String PERMISSION_SEPARATOR = RbacConstants.PERMISSION_SEPARATOR;

}
