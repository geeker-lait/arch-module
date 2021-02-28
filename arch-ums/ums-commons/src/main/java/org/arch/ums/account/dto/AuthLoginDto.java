package org.arch.ums.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.arch.framework.ums.enums.ChannelType;

import java.io.Serializable;

/**
 * Description:
 *
 * @author kenzhao
 * @author YongWu zheng
 * @date 2020/3/28 16:17
 */
@Data
@AllArgsConstructor
@Builder
public class AuthLoginDto implements Serializable {
    private static final long serialVersionUID = 1L;

    public AuthLoginDto() {
    }

    /**
     * identifierId
     */
    private Long id;
    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    private Long aid;
    /**
     * 租户 ID
     */
    private Integer tenantId;
    /**
     * 对应【credential】字段：站内账号是密码、第三方登录是Token；
     */
    private String credential;
    /**
     * 识别标识:身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号,
     * 对应 identifier 字段
     */
    private String identifier;
    /**
     * 登录类型【identityType】：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；
     */
    private ChannelType channelType;

    /**
     * 用户权限
     */
    private String authorities;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;


}
