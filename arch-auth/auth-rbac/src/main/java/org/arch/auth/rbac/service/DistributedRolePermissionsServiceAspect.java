package org.arch.auth.rbac.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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

/**
 * 主要功能: 从 redis 获取权限更新的相关信息, 根据获取的相关信息发布对应的权限更新事件.
 * @see top.dcenter.ums.security.core.api.premission.service.RolePermissionsServiceAspect
 * @author YongWu zheng
 * @version V2.0  Created by 2020/11/7 18:41
 */
@SuppressWarnings("unchecked")
@Slf4j
public class DistributedRolePermissionsServiceAspect implements RolePermissionsServiceAspect, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void handlerUpdateResourcesByRoleIdMethod(JoinPoint jp, boolean result, Long roleId, Long... resourceIds) {
        // TODO: 2021.3.7 从 redis 中获取相关信息, 根据相应的信息来决定是否发布权限更新事件.
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
                applicationContext.publishEvent(new UpdateRolesResourcesEvent(true, updateRoleResourcesDto));
            }
        }
    }

    @Override
    public void handlerUpdateResourcesByRoleIdOfTenantMethod(JoinPoint jp, boolean result, Long tenantId,
                                                             Long roleId, Long... resourceIds) {
        // TODO: 2021.3.7 从 redis 中获取相关信息, 根据相应的信息来决定是否发布权限更新事件.
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
                applicationContext.publishEvent(new UpdateRolesResourcesEvent(true, updateRoleResourcesDto));
            }
        }
    }

    @Override
    public void handlerUpdateResourcesByRoleIdOfScopeIdMethod(JoinPoint jp, boolean result, Long scopeId,
                                                              Long roleId, Long... resourceIds) {
        // TODO: 2021.3.7 从 redis 中获取相关信息, 根据相应的信息来决定是否发布权限更新事件.
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
                applicationContext.publishEvent(new UpdateRolesResourcesEvent(true, updateRoleResourcesDto));
            }
        }
    }

    @Override
    public void handlerUpdateRolesByGroupIdMethod(JoinPoint jp, boolean result,
                                                  Long groupId, Long... roleIds) {
        // TODO: 2021.3.7 从 redis 中获取相关信息, 根据相应的信息来决定是否发布权限更新事件.
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
                applicationContext.publishEvent(new UpdateRolesResourcesEvent(true, updateRoleResourcesDto));
            }
        }
    }

    @Override
    public void handlerUpdateRolesByGroupIdOfTenantMethod(JoinPoint jp, boolean result,
                                                          Long tenantId, Long groupId, Long... roleIds) {
        // TODO: 2021.3.7 从 redis 中获取相关信息, 根据相应的信息来决定是否发布权限更新事件.
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
                applicationContext.publishEvent(new UpdateRolesResourcesEvent(true, updateRoleResourcesDto));
            }
        }
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}