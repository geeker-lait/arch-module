package org.arch.auth.sso.rbac.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.core.api.premission.service.RolePermissionsService;
import top.dcenter.ums.security.core.api.premission.service.RolePermissionsServiceAspect;
import top.dcenter.ums.security.core.premission.dto.UpdateRoleResourcesDto;
import top.dcenter.ums.security.core.premission.enums.UpdateRolesResourcesType;
import top.dcenter.ums.security.core.premission.event.UpdateRolesResourcesEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static top.dcenter.ums.security.common.consts.TransactionalConstants.ONE_PRECEDENCE;

/**
 * 主要功能: <br>
 *     1. 当权限更新时, 发送更新消息到 redis, 以便其他微服务更新本地权限缓存.<br>
 *     2. 发布本地更新事件, 以便其他微服务更新本地权限缓存.
 * @see top.dcenter.ums.security.core.api.premission.service.RolePermissionsServiceAspect
 * @author YongWu zheng
 * @version V2.0  Created by 2020/11/7 18:41
 */
@SuppressWarnings("unchecked")
@Aspect
@Slf4j
@Order(ONE_PRECEDENCE)
public class PublishUpdateMessageAndLocalRolePermissionsServiceAspect implements RolePermissionsServiceAspect, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    @AfterReturning(pointcut = "execution(boolean *..updateResourcesByRoleId(..)) && args(roleId, resourceIds)",
            returning = "result", argNames = "jp, result, roleId, resourceIds")
    public void handlerUpdateResourcesByRoleIdMethod(JoinPoint jp, boolean result, Long roleId, Long... resourceIds) {
        if (jp.getTarget() instanceof RolePermissionsService) {
            RolePermissionsService<Object> rolePermissionsService = (RolePermissionsService<Object>) jp.getTarget();
            if (result) {
                Map<Long, List<Long>> roleIdResourcesIdMap =  new HashMap<>(1);
                roleIdResourcesIdMap.put(roleId,
                                         Arrays.stream(resourceIds)
                                               .collect(Collectors.toList()));
                UpdateRoleResourcesDto<Object> updateRoleResourcesDto =
                        UpdateRoleResourcesDto.builder()
                                              .updateType(UpdateRolesResourcesType.ROLE)
                                              .roleResources(roleIdResourcesIdMap)
                                              .resourceClass(rolePermissionsService.getUpdateResourcesClass())
                                              .build();
                // TODO: 2021.3.7 发布 redis 更新权限缓存消息
                applicationContext.publishEvent(new UpdateRolesResourcesEvent(true, updateRoleResourcesDto));
            }
        }
    }

    @Override
    @AfterReturning(pointcut = "execution(boolean *..updateResourcesByRoleIdOfTenant(..)) && args(tenantId, roleId, resourceIds)",
            returning = "result", argNames = "jp, result, tenantId, roleId, resourceIds")
    public void handlerUpdateResourcesByRoleIdOfTenantMethod(JoinPoint jp, boolean result, Long tenantId,
                                                             Long roleId, Long... resourceIds) {

        if (jp.getTarget() instanceof RolePermissionsService) {
            RolePermissionsService<Object> rolePermissionsService = (RolePermissionsService<Object>) jp.getTarget();
            if (result) {
                Map<Long, List<Long>> roleIdResourcesIdMap = new HashMap<>(1);
                roleIdResourcesIdMap.put(roleId,
                                         Arrays.stream(resourceIds)
                                               .collect(Collectors.toList()));
                UpdateRoleResourcesDto<Object> updateRoleResourcesDto =
                        UpdateRoleResourcesDto.builder()
                                              .tenantId(tenantId)
                                              .roleResources(roleIdResourcesIdMap)
                                              .updateType(UpdateRolesResourcesType.TENANT)
                                              .resourceClass(rolePermissionsService.getUpdateResourcesClass())
                                              .build();
                // TODO: 2021.3.7 发布 redis 更新权限缓存消息
                applicationContext.publishEvent(new UpdateRolesResourcesEvent(true, updateRoleResourcesDto));
            }
        }
    }

    @Override
    @AfterReturning(pointcut = "execution(boolean *..updateResourcesByScopeId(..)) && args(scopeId, roleId, resourceIds)",
            returning = "result", argNames = "jp, result, scopeId, roleId, resourceIds")
    public void handlerUpdateResourcesByRoleIdOfScopeIdMethod(JoinPoint jp, boolean result, Long scopeId,
                                                              Long roleId, Long... resourceIds) {
        if (jp.getTarget() instanceof RolePermissionsService) {
            RolePermissionsService<Object> rolePermissionsService = (RolePermissionsService<Object>) jp.getTarget();
            if (result) {
                Map<Long, List<Long>> roleIdResourcesIdMap = new HashMap<>(1);
                roleIdResourcesIdMap.put(roleId,
                                         Arrays.stream(resourceIds)
                                               .collect(Collectors.toList()));
                UpdateRoleResourcesDto<Object> updateRoleResourcesDto =
                        UpdateRoleResourcesDto.builder()
                                              .scopeId(scopeId)
                                              .updateType(UpdateRolesResourcesType.SCOPE)
                                              .roleResources(roleIdResourcesIdMap)
                                              .resourceClass(rolePermissionsService.getUpdateResourcesClass())
                                              .build();
                // TODO: 2021.3.7 发布 redis 更新权限缓存消息
                applicationContext.publishEvent(new UpdateRolesResourcesEvent(true, updateRoleResourcesDto));
            }
        }
    }

    @Override
    @AfterReturning(pointcut = "execution(boolean *..updateRolesByGroupId(..)) && args(groupId, roleIds)",
            returning = "result", argNames = "jp, result, groupId, roleIds")
    public void handlerUpdateRolesByGroupIdMethod(JoinPoint jp, boolean result,
                                                  Long groupId, Long... roleIds) {
        if (jp.getTarget() instanceof RolePermissionsService) {
            RolePermissionsService<Object> rolePermissionsService = (RolePermissionsService<Object>) jp.getTarget();
            if (result) {
                Map<Long, Set<Long>> groupIdRoleIdsMap = new HashMap<>(1);
                groupIdRoleIdsMap.put(groupId,
                                      Arrays.stream(roleIds)
                                            .collect(Collectors.toSet()));
                UpdateRoleResourcesDto<Object> updateRoleResourcesDto =
                        UpdateRoleResourcesDto.builder()
                                              .updateType(UpdateRolesResourcesType.GROUP)
                                              .groupRoles(groupIdRoleIdsMap)
                                              .resourceClass(rolePermissionsService.getUpdateResourcesClass())
                                              .build();
                // TODO: 2021.3.7 发布 redis 更新权限缓存消息
                applicationContext.publishEvent(new UpdateRolesResourcesEvent(true, updateRoleResourcesDto));
            }
        }
    }

    @Override
    @AfterReturning(pointcut = "execution(boolean *..updateRolesByGroupIdOfTenant(..)) && args(tenantId, groupId, roleIds)",
            returning = "result", argNames = "jp, result, tenantId, groupId, roleIds")
    public void handlerUpdateRolesByGroupIdOfTenantMethod(JoinPoint jp, boolean result,
                                                          Long tenantId, Long groupId, Long... roleIds) {
        if (jp.getTarget() instanceof RolePermissionsService) {
            RolePermissionsService<Object> rolePermissionsService = (RolePermissionsService<Object>) jp.getTarget();
            if (result) {
                Map<Long, Set<Long>> groupIdRoleIdsMap = new HashMap<>(1);
                groupIdRoleIdsMap.put(groupId,
                                      Arrays.stream(roleIds)
                                            .collect(Collectors.toSet()));
                UpdateRoleResourcesDto<Object> updateRoleResourcesDto =
                        UpdateRoleResourcesDto.builder()
                                              .tenantId(tenantId)
                                              .updateType(UpdateRolesResourcesType.GROUP)
                                              .groupRoles(groupIdRoleIdsMap)
                                              .resourceClass(rolePermissionsService.getUpdateResourcesClass())
                                              .build();
                // TODO: 2021.3.7 发布 redis 更新权限缓存消息
                applicationContext.publishEvent(new UpdateRolesResourcesEvent(true, updateRoleResourcesDto));
            }
        }
    }


    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}