package org.arch.auth.sso.username.service;

/**
 * 用户名关键字过滤服务
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.2 17:47
 */
public interface UsernameKeywordFilteringService {
    /**
     * 用户名是否有效
     * @param username  用户名
     * @return  用户名是否有效
     */
    Boolean isValid(String username);
}
