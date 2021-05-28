package org.arch.admin.rbac.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.stream.event.RbacRemotePermissionUpdatedEvent;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.ArgumentStatuesCode;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import top.dcenter.ums.security.core.permission.dto.UpdateRoleResourcesDto;
import top.dcenter.ums.security.core.permission.enums.UpdateRolesResourcesType;
import top.dcenter.ums.security.core.permission.event.UpdateRolesResourcesEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static java.util.Objects.isNull;
import static org.arch.framework.utils.SecurityUtils.isAdminForRole;
import static org.springframework.util.StringUtils.hasText;

/**
 * 用于权限同步更新缓存异常时用于人工干预
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController("adminRbacController")
@RequestMapping("/rbac")
@Slf4j
@RequiredArgsConstructor
public class RbacController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    /**
     * 发送权限更新事件, 用于权限同步更新缓存异常, 且对应的 RocketMq 死信队列有消息时, 通过人工调用此 API 接口处理, 重新发布更新事件.
     * 根据死信队列消息 {@code payload: {"type":"UpdateRoleResourcesDto","updateType":"TENANT","tenantId":0,"scopeId":null,
     * "groupRoles":null,"roleResources":{"1":[1,2,3]},"resourceClass":"org.arch.ums.account.entity.Permission"}}
     * 设置参数.<br>
     * 注意: 必须拥有 ROLE_ADMIN 角色才能上传.
     *
     * @param updateType     {@link UpdateRolesResourcesType}, ALL/GROUP/ROLE/SCOPE/TENANT
     * @param id             角色 id, 当 {@code updateType} 为 {@code GROUP} 时为 groupId,
     *                       当 {@code updateType} 为 {@code ALL} 时为 null
     * @param ids            权限 ids, 当 {@code updateType} 为 {@code GROUP} 时为 roleIds,
     *                       当 {@code updateType} 为 {@code ALL} 时为 null
     * @param permissionType 权限类型, 如: org.arch.ums.account.entity.Permission
     * @param tenantId       id 与 ids 所属的租户 id, 当 {@code updateType} 为 {@code ALL/SCOPE/ROLE} 时为 null
     * @return 是否成功发送权限更新事件
     */
    @PostMapping("/publishingUpdateEvent/{updateType}")
    public Response<Boolean> publisherPermissionUpdateEvent(@PathVariable("updateType") String updateType,
                                                            @RequestParam(value = "id", required = false) Long id,
                                                            @RequestParam(value = "ids", required = false) List<Long> ids,
                                                            @RequestParam(value = "permissionType", required = false) String permissionType,
                                                            @RequestParam(value = "tenantId", required = false) Long tenantId,
                                                            TokenInfo token) {

        if (!isAdminForRole(token)) {
            return Response.failed(AuthStatusCode.FORBIDDEN);
        }

        UpdateRolesResourcesType updateRolesResourcesType;
        try {
            updateRolesResourcesType = UpdateRolesResourcesType.valueOf(updateType.toUpperCase());
        } catch (Exception e) {
            return Response.failed(ArgumentStatuesCode.VALIDATE_FAILED,
                    "updateType 必须是 TENANT/SCOPE/ROLE/GROUP/ALL 之一");
        }

        switch (updateRolesResourcesType) {
            case TENANT:
                return publishingEventOfTenant(id, ids, permissionType, tenantId);
            case SCOPE:
                return publishingEventOfScope(id, ids, permissionType);
            case GROUP:
                return publishingEventOfGroup(id, ids, tenantId);
            case ROLE:
                return publishingEventOfRole(id, ids, permissionType);
            case ALL:
                return publishingAllEvent();
            default:
                return Response.failed(ArgumentStatuesCode.VALIDATE_FAILED,
                        "updateType 必须是 TENANT/SCOPE/ROLE/GROUP/ALL 之一");
        }

    }

    private Response<Boolean> publishingEventOfGroup(Long id, List<Long> ids, Long tenantId) {
        if (isNull(tenantId) || isNull(id) || isNull(ids)) {
            return Response.failed(ArgumentStatuesCode.VALIDATE_FAILED,
                    "tenantId, id, ids cannot be null");
        }

        UpdateRoleResourcesDto<Object> dto =
                UpdateRoleResourcesDto.builder()
                        .updateType(UpdateRolesResourcesType.GROUP)
                        .tenantId(tenantId)
                        .groupRoles(Collections.singletonMap(id, new HashSet<>(ids)))
                        .build();
        this.publisher.publishEvent(new UpdateRolesResourcesEvent(this,
                dto,
                new RbacRemotePermissionUpdatedEvent(this,
                        dto)));

        return Response.success(Boolean.TRUE);
    }

    private Response<Boolean> publishingEventOfScope(Long id, List<Long> ids, String permissionType) {
        // 获取要更新的资源类型
        //noinspection unchecked
        Class<Object> resourceClass = (Class<Object>) getResourceClass(permissionType);

        if (isNull(id) || isNull(ids)) {
            return Response.failed(ArgumentStatuesCode.VALIDATE_FAILED,
                    "id, ids cannot be null");
        }

        if (isNull(resourceClass)) {
            return Response.failed(ArgumentStatuesCode.VALIDATE_FAILED,
                    "permissionType 错误");
        }
        UpdateRoleResourcesDto<Object> dto =
                UpdateRoleResourcesDto.builder()
                        .updateType(UpdateRolesResourcesType.SCOPE)
                        .resourceClass(resourceClass)
                        .roleResources(Collections.singletonMap(id, ids))
                        .build();
        this.publisher.publishEvent(new UpdateRolesResourcesEvent(this,
                dto,
                new RbacRemotePermissionUpdatedEvent(this,
                        dto)));

        return Response.success(Boolean.TRUE);
    }

    private Response<Boolean> publishingEventOfRole(Long id, List<Long> ids, String permissionType) {
        if (isNull(id) || isNull(ids)) {
            return Response.failed(ArgumentStatuesCode.VALIDATE_FAILED,
                    "id, ids cannot be null");
        }

        // 获取要更新的资源类型
        //noinspection unchecked
        Class<Object> resourceClass = (Class<Object>) getResourceClass(permissionType);
        if (isNull(resourceClass)) {
            return Response.failed(ArgumentStatuesCode.VALIDATE_FAILED,
                    "permissionType 错误");
        }
        UpdateRoleResourcesDto<Object> dto =
                UpdateRoleResourcesDto.builder()
                        .updateType(UpdateRolesResourcesType.ROLE)
                        .resourceClass(resourceClass)
                        .roleResources(Collections.singletonMap(id, ids))
                        .build();
        this.publisher.publishEvent(new UpdateRolesResourcesEvent(this,
                dto,
                new RbacRemotePermissionUpdatedEvent(this,
                        dto)));

        return Response.success(Boolean.TRUE);
    }

    private Response<Boolean> publishingEventOfTenant(Long id, List<Long> ids, String permissionType, Long tenantId) {

        if (isNull(tenantId) || isNull(id) || isNull(ids)) {
            return Response.failed(ArgumentStatuesCode.VALIDATE_FAILED,
                    "tenantId, id, ids cannot be null");
        }

        // 获取要更新的资源类型
        //noinspection unchecked
        Class<Object> resourceClass = (Class<Object>) getResourceClass(permissionType);
        if (isNull(resourceClass)) {
            return Response.failed(ArgumentStatuesCode.VALIDATE_FAILED,
                    "permissionType 错误");
        }
        UpdateRoleResourcesDto<Object> dto =
                UpdateRoleResourcesDto.builder()
                        .updateType(UpdateRolesResourcesType.TENANT)
                        .tenantId(tenantId)
                        .resourceClass(resourceClass)
                        .roleResources(Collections.singletonMap(id, ids))
                        .build();
        this.publisher.publishEvent(new UpdateRolesResourcesEvent(this,
                dto,
                new RbacRemotePermissionUpdatedEvent(this,
                        dto)));

        return Response.success(Boolean.TRUE);
    }

    private Response<Boolean> publishingAllEvent() {
        UpdateRoleResourcesDto<Object> dto =
                UpdateRoleResourcesDto.builder()
                        .updateType(UpdateRolesResourcesType.ALL)
                        .build();
        this.publisher.publishEvent(new UpdateRolesResourcesEvent(this,
                dto,
                new RbacRemotePermissionUpdatedEvent(this,
                        dto)));

        return Response.success(Boolean.TRUE);
    }

    private Class<?> getResourceClass(String permissionType) {
        if (hasText(permissionType)) {
            try {
                return Class.forName(permissionType);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}
