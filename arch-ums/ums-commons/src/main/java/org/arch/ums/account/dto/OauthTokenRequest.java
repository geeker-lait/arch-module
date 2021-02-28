package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 第三方账号授权(OauthToken) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:55:50
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OauthTokenRequest {

    /**
     * account_oauth_token id
     */
    private Long id;

    /**
     * AccountIdentifierId
     */
    private Long accountIdentifierId;

    /**
     * 租户 id
     */
    private Integer tenantId;

    /**
     * 是否支持 refreshToken, 默认: 0. 1 表示支持, 0 表示不支持
     */
    private Boolean enableRefresh;

    /**
     * 第三方服务商,如: qq,github
     */
    private String providerId;

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * accessToken过期时间 无过期时间默认为 -1
     */
    private Long expireIn;

    /**
     * refreshToken过期时间 无过期时间默认为 -1
     */
    private Long refreshTokenExpireIn;

    /**
     * refreshToken
     */
    private String refreshToken;

    /**
     * alipay userId
     */
    private String uid;

    /**
     * qq/mi/toutiao/wechatMp/wechatOpen/weibo/jd/kujiale/dingTalk/douyin/feishu
     */
    private String openId;

    /**
     * dingTalk, taobao 附带属性
     */
    private String accessCode;

    /**
     * QQ附带属性
     */
    private String unionId;

    /**
     * Google附带属性
     */
    private String scope;

    /**
     * Google附带属性
     */
    private String tokenType;

    /**
     * Google附带属性
     */
    private String idToken;

    /**
     * 小米附带属性
     */
    private String macAlgorithm;

    /**
     * 小米附带属性
     */
    private String macKey;

    /**
     * 企业微信附带属性
     */
    private String code;

    /**
     * Twitter附带属性
     */
    private String oauthToken;

    /**
     * Twitter附带属性
     */
    private String oauthTokenSecret;

    /**
     * Twitter附带属性
     */
    private String userId;

    /**
     * Twitter附带属性
     */
    private String screenName;

    /**
     * Twitter附带属性
     */
    private String oauthCallbackConfirmed;

    /**
     * 过期时间, 基于 1970-01-01T00:00:00Z, 无过期时间默认为 -1
     */
    private Long expireTime;

    /**
     * 时间戳
     */
    private LocalDateTime st;

}