package test.auth.ums.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.dto.BaseSearchDto;
import org.arch.framework.ums.enums.ChannelType;

import java.util.Map;

/**
 * 用户-标识(AccountIdentifier) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-24 23:17:45
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AccountIdentifierSearchDto extends BaseSearchDto {

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
     * 租户 id
     */
    private String tenantId;

    /**
     * 用户角色:ROLE_xxx 与 租户id: TENANT_XXX
     */
    private String authorities;

    /**
     * 登录类型【IDENTITYTYPE】：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；
     */
    private ChannelType channelType;

    @Override
    protected void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_aid", this.getAid(), map);
        putNoNull("EQ_identifier", this.getIdentifier(), map);
        putNoNull("EQ_credential", this.getCredential(), map);
        putNoNull("EQ_tenantId", this.getTenantId(), map);
        putNoNull("EQ_authorities", this.getAuthorities(), map);
        putNoNull("EQ_channelType", this.getChannelType(), map);
    }
}