package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;
import org.arch.framework.encrypt.EncryptField;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 第三方账号授权(OauthToken) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:25:28
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_oauth_token")
public class OauthToken extends CrudEntity<OauthToken> {
    private static final long serialVersionUID = 1L;

    /**
     * account_oauth_token id
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * AccountIdentifierId
     */
    @TableField(value = "`account_identifier_id`")
    private Long accountIdentifierId;

    /**
     * 租户 id
     */
    @TableField(value = "`tenant_id`")
    private Integer tenantId;

    /**
     * 是否支持 refreshToken, 默认: 0. 1 表示支持, 0 表示不支持
     */
    @TableField(value = "`enable_refresh`")
    private Boolean enableRefresh;

    /**
     * 第三方服务商,如: qq,github
     */
    @TableField(value = "`provider_id`")
    private String providerId;

    /**
     * accessToken
     */
    @TableField(value = "`access_token`")
    @EncryptField(encryptType = "TEXT_ENCRYPT")
    private String accessToken;

    /**
     * accessToken过期时间 无过期时间默认为 -1
     */
    @TableField(value = "`expire_in`")
    private Long expireIn;

    /**
     * refreshToken过期时间 无过期时间默认为 -1
     */
    @TableField(value = "`refresh_token_expire_in`")
    private Long refreshTokenExpireIn;

    /**
     * refreshToken
     */
    @TableField(value = "`refresh_token`")
    @EncryptField(encryptType = "TEXT_ENCRYPT")
    private String refreshToken;

    /**
     * alipay userId
     */
    @TableField(value = "`uid`")
    private String uid;

    /**
     * qq/mi/toutiao/wechatMp/wechatOpen/weibo/jd/kujiale/dingTalk/douyin/feishu
     */
    @TableField(value = "`open_id`")
    private String openId;

    /**
     * dingTalk, taobao 附带属性
     */
    @TableField(value = "`access_code`")
    @EncryptField(encryptType = "TEXT_ENCRYPT")
    private String accessCode;

    /**
     * QQ附带属性
     */
    @TableField(value = "`union_id`")
    private String unionId;

    /**
     * Google附带属性
     */
    @TableField(value = "`scope`")
    private String scope;

    /**
     * Google附带属性
     */
    @TableField(value = "`token_type`")
    private String tokenType;

    /**
     * Google附带属性
     */
    @TableField(value = "`id_token`")
    private String idToken;

    /**
     * 小米附带属性
     */
    @TableField(value = "`mac_algorithm`")
    private String macAlgorithm;

    /**
     * 小米附带属性
     */
    @TableField(value = "`mac_key`")
    private String macKey;

    /**
     * 企业微信附带属性
     */
    @TableField(value = "`code`")
    private String code;

    /**
     * Twitter附带属性
     */
    @TableField(value = "`oauth_token`")
    private String oauthToken;

    /**
     * Twitter附带属性
     */
    @TableField(value = "`oauth_token_secret`")
    @EncryptField(encryptType = "TEXT_ENCRYPT")
    private String oauthTokenSecret;

    /**
     * Twitter附带属性
     */
    @TableField(value = "`user_id`")
    private String userId;

    /**
     * Twitter附带属性
     */
    @TableField(value = "`screen_name`")
    private String screenName;

    /**
     * Twitter附带属性
     */
    @TableField(value = "`oauth_callback_confirmed`")
    private String oauthCallbackConfirmed;

    /**
     * 过期时间, 基于 1970-01-01T00:00:00Z, 无过期时间默认为 -1
     */
    @TableField(value = "`expire_time`")
    private Long expireTime;

    /**
     * 时间戳/创建时间
     */
    @TableField(value = "`dt`")
    private LocalDateTime dt;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
