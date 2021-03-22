package org.arch.auth.rbac.stream.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 用于权限更新后发送权限更新事件
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.20 13:05
 */
public interface RbacSource {

    String RBAC_PERMISSION_UPDATE_PRODUCER = "rbacPermissionUpdateProducer";

    /**
     * 发送权限更新消息到 MQ 的 output
     * @return  {@link SubscribableChannel}
     */
    @Output(RBAC_PERMISSION_UPDATE_PRODUCER)
    MessageChannel rbacPermissionUpdateProducer();
}
