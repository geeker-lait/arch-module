package org.arch.auth.sso.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户认证成功后, 返回前端数据封装对象
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.6 23:46
 */
@Data
@Builder
public class AuthTokenVo implements Serializable {
    private static final long serialVersionUID = -4163311098538472532L;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * token 如: jwt
     */
    private String token;
    /**
     * refresh token
     */
    private String refreshToken;

}
