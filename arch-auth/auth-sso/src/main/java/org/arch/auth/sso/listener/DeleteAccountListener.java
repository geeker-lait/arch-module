package org.arch.auth.sso.listener;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.event.DeleteAccountEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 删除账号监听器
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.6 13:17
 */
@Component
@Slf4j
public class DeleteAccountListener implements ApplicationListener<DeleteAccountEvent> {
    @Override
    public void onApplicationEvent(@NonNull DeleteAccountEvent event) {
        // 记录日志
        log.info("删除账号事件: accountId: {}", event.getAccountId());
    }
}
