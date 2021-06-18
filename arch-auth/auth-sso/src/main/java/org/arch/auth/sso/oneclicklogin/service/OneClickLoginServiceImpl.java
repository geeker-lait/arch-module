package org.arch.auth.sso.oneclicklogin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import top.dcenter.ums.security.core.api.oauth.oneclicklogin.service.OneClickLoginService;
import top.dcenter.ums.security.core.exception.Auth2Exception;

import java.util.Map;

import static java.util.Objects.isNull;

/**
 * 一键登录服务：根据 accessToken 从服务商获取用户手机号
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.14 20:01
 */
@Slf4j
@Component
public class OneClickLoginServiceImpl implements OneClickLoginService {

    @Override
    @NonNull
    public String callback(@NonNull String accessToken, @Nullable Map<String, String> otherParamMap) throws Auth2Exception {
        // TODO: 2021.5.14 待实现
        return "13012345678";
    }

    @Override
    public void otherParamsHandler(@NonNull UserDetails userDetails, Map<String, String> otherParamMap) {
        log.debug("login username: {}", userDetails.getUsername());
        if (isNull(otherParamMap)) {
        	return;
        }
        // TODO: 2021.5.22 待实现
        log.debug("request other params: {}", otherParamMap.toString());

    }
}
