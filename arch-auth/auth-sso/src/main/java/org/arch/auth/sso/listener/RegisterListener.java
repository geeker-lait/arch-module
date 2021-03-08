package org.arch.auth.sso.listener;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.event.RegisterEvent;
import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 注册事件监听器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.6 12:57
 */
@Component
@Slf4j
public class RegisterListener implements ApplicationListener<RegisterEvent> {

    @Override
    public void onApplicationEvent(@NonNull RegisterEvent event) {
        ArchUser archUser = event.getArchUser();
        // 记录日志
        log.info("用户注册成功: 租户: {}, identifier: {}, aid: {}, channelType: {}, source: {}",
                 archUser.getTenantId(), archUser.getUsername(), archUser.getAccountId(),
                 archUser.getChannelType().name(), event.getSource());

    }
}
