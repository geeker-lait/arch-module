package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 授权客户端(account_oauth_client)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@SuppressWarnings("Lombok")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_oauth_client")
public class AccountOauthClient extends Model<AccountOauthClient> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 授权客户端ID
     */
    @TableId
	private Long id;
    /**
     * appId 或 客户端ID
     */
    private String appId;
    /**
     * appSecret 或 客户端secret
     */
    private String appCode;
    /**
     * openid/userinfo/token/code/资源服务器标识等
     */
    private String scopes;
    /**
     * 客户端类型: web, 安卓, ios, 小程序…
     */
    private String appType;

}