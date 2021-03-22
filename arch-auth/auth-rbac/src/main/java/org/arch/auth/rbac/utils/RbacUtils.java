package org.arch.auth.rbac.utils;

import org.arch.auth.rbac.stream.event.RbacRemotePermissionUpdatedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.core.premission.dto.UpdateRoleResourcesDto;
import top.dcenter.ums.security.core.premission.event.UpdateRolesResourcesEvent;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.21 11:45
 */
public class RbacUtils {

    /**
     * publish event({@link UpdateRolesResourcesEvent})
     * @param updateRoleResourcesDto    {@link UpdateRoleResourcesDto}
     * @param source                    {@link ApplicationEvent#getSource()}
     * @param applicationEventPublisher application event publisher
     * @param isRemote                  whether to send remote events
     */
    public static void publishEvent(@NonNull UpdateRoleResourcesDto<Object> updateRoleResourcesDto,
                              @NonNull Object source,
                              @NonNull ApplicationEventPublisher applicationEventPublisher,
                              @NonNull Boolean isRemote) {
        // 构建发送 MQ 的权限更新事件
        RbacRemotePermissionUpdatedEvent remoteApplicationEvent = null;
        if (isRemote) {
            remoteApplicationEvent = new RbacRemotePermissionUpdatedEvent(source, updateRoleResourcesDto);
        }
        applicationEventPublisher.publishEvent(new UpdateRolesResourcesEvent(source,
                                                                             updateRoleResourcesDto,
                                                                             remoteApplicationEvent));
    }
}
