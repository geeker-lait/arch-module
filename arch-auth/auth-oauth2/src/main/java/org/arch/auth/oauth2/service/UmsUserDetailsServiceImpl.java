
package org.arch.auth.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.arch.ums.account.biz.IAccountBiz;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import top.dcenter.ums.security.core.api.service.UmsUserDetailsService;
import top.dcenter.ums.security.core.exception.RegisterUserFailureException;

import java.io.IOException;
import java.util.List;

/**
 * 用户登录服务接口实现
 * @author YongWu zheng
 * @vrsion V2.0  Created by 2020.12.27 15:08
 */
@Service
@RequiredArgsConstructor
@Primary
public class UmsUserDetailsServiceImpl implements UmsUserDetailsService {

    private final IAccountBiz iAccountBiz;

    @Override
    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        // TODO
        return null;
    }

    @Override
    public List<Boolean> existedByUsernames(String... usernames) throws IOException {
        // TODO
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO
        return null;
    }

    @Override
    public UserDetails registerUser(String mobile) throws RegisterUserFailureException {
        // TODO
        return null;
    }

    @Override
    public UserDetails registerUser(ServletWebRequest request) throws RegisterUserFailureException {
        // TODO
        return null;
    }
}
