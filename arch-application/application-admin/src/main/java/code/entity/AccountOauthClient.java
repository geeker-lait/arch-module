package code.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 账号-授权客户端(account_oauth_client)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_oauth_client")
public class AccountOauthClient extends Model<AccountOauthClient> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 客户端 ID
     */
    private String appId;
    /**
     * 客户端secret
     */
    private String appCode;
    /**
     * scope id 列表, 如: openid/userinfo/token/code/资源服务器标识等
     */
    private String scopes;
    /**
     * 客户端类型: web, 安卓, ios, 小程序…
     */
    private Integer appType;

}