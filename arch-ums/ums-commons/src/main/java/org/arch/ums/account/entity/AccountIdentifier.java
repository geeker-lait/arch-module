package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户-标识(account_identifier)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_identifier")
public class AccountIdentifier extends Model<AccountIdentifier> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * AccountIdentifier ID
     */
    @TableId
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
     * 用户权限
     */
    private String authorities;
    /**
     * 登录类型【IDENTITY_TYPE】：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；
     */
    private String channelType;

}