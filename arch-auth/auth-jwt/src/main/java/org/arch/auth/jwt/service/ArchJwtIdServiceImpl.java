package org.arch.auth.jwt.service;

import lombok.RequiredArgsConstructor;
import org.arch.framework.id.IdKey;
import org.arch.framework.id.IdService;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.jwt.api.id.service.JwtIdService;

/**
 * jwt jti 生成器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 17:24
 */
@RequiredArgsConstructor
public class ArchJwtIdServiceImpl implements JwtIdService {

    private final IdService idService;

    @NonNull
    @Override
    public String generateJtiId() {
        return idService.generateId(IdKey.JWT_JTI);
    }

    @NonNull
    @Override
    public String generateRefreshToken() {
        return idService.generateId(IdKey.JWT_REFRESH_TOKEN);
    }
}