package org.arch.auth.sso.jwt.service;

import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.jwt.api.validator.service.CustomClaimValidateService;

import java.time.Instant;

/**
 * 对 {@link JwtClaimNames#EXP} 进行有效性校验, 如果是 JWT + redis 缓存模式, 则校验 redis 缓存是否存在.
 * @author YongWu zheng
 * @version V2.0  Created by 2020.12.20 21:47
 */
@Service
public class ArchExpCustomClaimValidateServiceImpl implements CustomClaimValidateService {

    @Override
    public boolean validate(Object claimObject) {
        Instant exp = (Instant) claimObject;
        // TODO
        if (Instant.now().isAfter(exp)) {
            return false;
        }
        return true;
    }

    @Override
    public String getClaimName() {
        return JwtClaimNames.EXP;
    }

}