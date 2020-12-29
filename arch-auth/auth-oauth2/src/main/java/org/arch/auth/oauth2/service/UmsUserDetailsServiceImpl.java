
package org.arch.auth.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.ServletWebRequest;
import top.dcenter.ums.security.core.api.service.UmsUserDetailsService;
import top.dcenter.ums.security.core.exception.RegisterUserFailureException;

import java.io.IOException;
import java.util.List;

/**
 * @author YongWu zheng
 * @version V2.0  Created by 2020.12.27 15:08
 */
public class UmsUserDetailsServiceImpl implements UmsUserDetailsService {

    @Autowired


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
