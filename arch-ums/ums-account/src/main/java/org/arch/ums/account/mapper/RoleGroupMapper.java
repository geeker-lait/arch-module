package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.RoleGroup;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * 账号-角色组织或机构(RoleGroup) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:25:31
 * @since 1.0.0
 */
@Mapper
public interface RoleGroupMapper extends CrudMapper<RoleGroup> {

    /**
     * 基于多租户, 查询指定角色组 {@code groupId} 所拥有的所有角色集合, Set(roleAuthority).
     *
     * @param tenantId 多租户 ID
     * @param groupId  角色组 ID
     * @return groupId 所拥有的所有角色集合, Set(roleAuthority).
     */
    @Select(value = "SELECT `role_name` FROM `account_role` WHERE `tenant_id` = #{tenantId} AND `deleted` = 0 AND " +
            "`id` IN(SELECT `role_id` FROM `account_role_group` WHERE `tenant_id` = #{tenantId} " +
                                                                      "AND `group_id` = #{groupId} AND `deleted` = 0);")
    Set<String> findRolesByGroupIdOfTenant(@NonNull @Param("tenantId") Long tenantId,
                                           @NonNull @Param("groupId") Long groupId);
}