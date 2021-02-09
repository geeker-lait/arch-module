package org.arch.auth.sso.username.service.impl;

import org.arch.auth.sso.username.service.UsernameKeywordFilteringService;
import org.springframework.stereotype.Component;

/**
 * 用户名关键字过滤服务实现
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.2 17:54
 */
@Component
public class ArchUsernameKeywordFilteringServiceImpl implements UsernameKeywordFilteringService {
    @Override
    public Boolean isValid(String username) {
        // TODO 待实现
        return true;
    }
}
