package org.arch.application.demo.ums.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.ums.enums.LoginType;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户-标识(Identifier) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:52:47
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class IdentifierSearchDto implements BaseSearchDto {

    /**
     * AccountIdentifier ID
     */
    private Long id;

    /**
     * 账号名ID
     */
    private Long aid;

    /**
     * 识别标识:身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号；
     */
    private String identifier;

    /**
     * 授权凭证【CREDENTIAL】：站内账号是密码、第三方登录是Token；
     */
    private String credential;

    /**
     * 用户角色:ROLE_xxx 与 租户id: TENANT_XXX
     */
    private String authorities;

    /**
     * 登录类型：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；
     */
    private LoginType loginType;

    /**
     * 租户 id
     */
    private Integer tenantId;

    /**
     * 应用 id
     */
    private Integer appId;

    /**
     * 店铺 id
     */
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    private LocalDateTime st;

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_identifier", this.getIdentifier(), map);
        putNoNull("EQ_aid", this.getAid(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_credential", this.getCredential(), map);
        putNoNull("EQ_authorities", this.getAuthorities(), map);
        putNoNull("EQ_login_type", this.getLoginType(), map);
        putNoNull("EQ_st", this.getSt(), map);
    }
}
