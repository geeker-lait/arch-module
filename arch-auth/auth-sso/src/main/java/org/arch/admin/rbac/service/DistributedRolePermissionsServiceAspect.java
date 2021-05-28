package org.arch.admin.rbac.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import top.dcenter.ums.security.core.api.permission.service.RolePermissionsService;
import top.dcenter.ums.security.core.api.permission.service.RolePermissionsServiceAspect;
import top.dcenter.ums.security.core.permission.dto.UpdateRoleResourcesDto;
import top.dcenter.ums.security.core.permission.enums.UpdateRolesResourcesType;

import java.util.*;
import java.util.stream.Collectors;

import static org.arch.auth.rbac.utils.RbacUtils.publishEvent;
import static top.dcenter.ums.security.common.consts.TransactionalConstants.ONE_PRECEDENCE;

/**
 * 主要功能: <br>
 * 1. 当权限更新时, 发送更新消息到 MQ, 以便其他微服务更新本地权限缓存.<br>
 * 2. 发布本地更新事件, 以便其他微服务更新本地权限缓存.
 *
 * @author YongWu zheng
 * @version V2.0  Created by 2020/11/7 18:41
 * @see top.dcenter.ums.security.core.api.permission.service.RolePermissionsServiceAspect
 */
@SuppressWarnings("unchecked")
@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
@Order(ONE_PRECEDENCE)
public class DistributedRolePermissionsServiceAspect implements RolePermissionsServiceAspect, ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    @AfterReturning(pointcut = "execution(boolean *..updateResourcesByRoleId(..)) && args(roleId, resourceIds)",
            returning = "result", argNames = "jp, result, roleId, resourceIds")
    public void handlerUpdateResourcesByRoleIdMethod(JoinPoint jp, boolean result, Long roleId, Long... resourceIds) {
        if (jp.getTarget() instanceof RolePermissionsService) {
            RolePermissionsService<Object> rolePermissionsService = (RolePermissionsService<Object>) jp.getTarget();
            if (result) {
                Map<Long, List<Long>> roleIdResourcesIdMap = new HashMap<>(1);
                roleIdResourcesIdMap.put(roleId,
                        Arrays.stream(resourceIds)
                                .collect(Collectors.toList()));
                UpdateRoleResourcesDto<Object> updateRoleResourcesDto =
                        UpdateRoleResourcesDto.builder()
                                .updateType(UpdateRolesResourcesType.ROLE)
                                .roleResources(roleIdResourcesIdMap)
                                .resourceClass(rolePermissionsService.getUpdateResourcesClass())
                                .build();
                publishEvent(updateRoleResourcesDto, this, applicationEventPublisher, Boolean.TRUE);
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
                publishEvent(updateRoleResourcesDto, this, applicationEventPublisher, Boolean.TRUE);
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
                publishEvent(updateRoleResourcesDto, this, applicationEventPublisher, Boolean.TRUE);
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
                publishEvent(updateRoleResourcesDto, this, applicationEventPublisher, Boolean.TRUE);
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
                publishEvent(updateRoleResourcesDto, this, applicationEventPublisher, Boolean.TRUE);
            }
        }
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

}