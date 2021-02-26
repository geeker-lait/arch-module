package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 第三方账号授权(OauthToken) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:55:50
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OauthTokenSearchDto implements BaseSearchDto {

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

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_account_identifier_id", this.getAccountIdentifierId(), map);
        putNoNull("EQ_enable_refresh", this.getEnableRefresh(), map);
        putNoNull("EQ_expire_time", this.getExpireTime(), map);
        putNoNull("EQ_st", this.getSt(), map);
        putNoNull("EQ_provider_id", this.getProviderId(), map);
        putNoNull("EQ_access_token", this.getAccessToken(), map);
        putNoNull("EQ_expire_in", this.getExpireIn(), map);
        putNoNull("EQ_refresh_token", this.getRefreshToken(), map);
        putNoNull("EQ_refresh_token_expire_in", this.getRefreshTokenExpireIn(), map);
        putNoNull("EQ_uid", this.getUid(), map);
        putNoNull("EQ_open_id", this.getOpenId(), map);
        putNoNull("EQ_access_code", this.getAccessCode(), map);
        putNoNull("EQ_union_id", this.getUnionId(), map);
        putNoNull("EQ_scope", this.getScope(), map);
        putNoNull("EQ_token_type", this.getTokenType(), map);
        putNoNull("EQ_id_token", this.getIdToken(), map);
        putNoNull("EQ_mac_algorithm", this.getMacAlgorithm(), map);
        putNoNull("EQ_mac_key", this.getMacKey(), map);
        putNoNull("EQ_code", this.getCode(), map);
        putNoNull("EQ_oauth_token", this.getOauthToken(), map);
        putNoNull("EQ_oauth_token_secret", this.getOauthTokenSecret(), map);
        putNoNull("EQ_user_id", this.getUserId(), map);
        putNoNull("EQ_screen_name", this.getScreenName(), map);
        putNoNull("EQ_oauth_callback_confirmed", this.getOauthCallbackConfirmed(), map);
    }
}
