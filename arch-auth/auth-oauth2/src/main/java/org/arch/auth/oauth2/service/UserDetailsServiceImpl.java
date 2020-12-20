package org.arch.auth.oauth2.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.arch.auth.oauth2.constant.MessageConstant;
import org.arch.auth.oauth2.domain.SecurityUser;
import org.arch.auth.oauth2.domain.UserDTO;
import org.arch.auth.rbac.entity.RbacUsername;
import org.arch.auth.rbac.entity.RbacVerification;
import org.arch.auth.rbac.mapper.RbacUsernameMapper;
import org.arch.auth.rbac.mapper.RbacVerificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户管理业务类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private List<UserDTO> userList;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RbacUsernameMapper rbacUsernameDao;

    @Autowired
    private RbacVerificationMapper rbacVerificationDao;


    @PostConstruct

    public void initData() {
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new UserDTO(1L, "yonghui", password, 1, CollUtil.toList("ADMIN")));
        userList.add(new UserDTO(2L, "lait", password, 1, CollUtil.toList("TEST")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RbacUsername rbacUsername = new RbacUsername();
        rbacUsername.setUsername(username);
        rbacUsername = rbacUsernameDao.selectOne(new QueryWrapper<>(rbacUsername));


        //List<UserDTO> findUserList = userList.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if (Objects.isNull(rbacUsername)) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }

        RbacVerification rbacVerification = rbacVerificationDao.selectById(rbacUsername.getId());

        UserDTO userDTO = UserDTO.builder().username(rbacUsername.getUsername())
                //.roles()
                .build();
        SecurityUser securityUser = new SecurityUser();
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }

}
