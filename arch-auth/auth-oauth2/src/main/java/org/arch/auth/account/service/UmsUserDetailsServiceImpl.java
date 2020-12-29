/*
 * MIT License
 * Copyright (c) 2020-2029 YongWu zheng (dcenter.top and gitee.com/pcore and github.com/ZeroOrInfinity)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.arch.auth.account.service;

import me.zhyd.oauth.model.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.ServletWebRequest;
import top.dcenter.ums.security.core.api.service.UmsUserDetailsService;
import top.dcenter.ums.security.core.exception.RegisterUserFailureException;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author YongWu zheng
 * @since 2020.12.29 20:13
 */
public class UmsUserDetailsServiceImpl implements UmsUserDetailsService {

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

    @Override
    public UserDetails registerUser(AuthUser authUser, String username, String defaultAuthority) throws RegisterUserFailureException {
        // TODO
        return null;
    }

    @Override
    public UserDetails registerUser(AuthUser authUser, String username, String defaultAuthority, String decodeState) throws RegisterUserFailureException {
        // TODO
        return null;
    }
}
