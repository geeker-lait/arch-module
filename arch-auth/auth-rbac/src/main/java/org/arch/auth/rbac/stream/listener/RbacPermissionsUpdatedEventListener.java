package org.arch.auth.rbac.stream.listener;

import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.stream.event.RbacRemotePermissionUpdatedEvent;
import org.arch.auth.rbac.stream.service.RbacMqReceiverOrSenderService;
import org.springframework.context.event.EventListener;
import top.dcenter.ums.security.core.permission.event.UpdateRolesResourcesEvent;

/**
 * 主要功能: 通过监听 Role Permissions 更新的事件, 并发布 {@link UpdateRolesResourcesEvent}
 * @see top.dcenter.ums.security.core.api.permission.service.RolePermissionsServiceAspect
 * @author YongWu zheng
 * @version V2.0  Created by 2020/11/7 18:41
 */
@Slf4j
public class RbacPermissionsUpdatedEventListener {

    private final RbacMqReceiverOrSenderService rbacMqReceiverOrSenderService;

    public RbacPermissionsUpdatedEventListener(RbacMqReceiverOrSenderService rbacMqReceiverOrSenderService) {
        this.rbacMqReceiverOrSenderService = rbacMqReceiverOrSenderService;
    }

    @EventListener(classes = {RbacRemotePermissionUpdatedEvent.class})
    public void rolePermissionsUpdatedHandler(RbacRemotePermissionUpdatedEvent event) {
        // 给 MQ 发送权限更新消息
        String keys = rbacMqReceiverOrSenderService.send(event.getUpdateRoleResourcesDto());
        log.info("Send permission update message to MQ: keys={}, When the keys are empty, it means sending failed", keys);
    }

}