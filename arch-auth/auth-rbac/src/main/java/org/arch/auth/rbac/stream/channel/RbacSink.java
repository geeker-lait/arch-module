package org.arch.auth.rbac.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 用于从 MQ 接收权限事件
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.20 20:27
 */
public interface RbacSink {

    String RBAC_PERMISSION_UPDATE_CONSUME = "rbacPermissionUpdateConsume";

    /**
     * 从 MQ 接收权限事件的 input
     * @return  {@link SubscribableChannel}
     */
    @Input(RBAC_PERMISSION_UPDATE_CONSUME)
    SubscribableChannel rbacPermissionUpdateConsume();
}
