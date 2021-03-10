package code.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 账号-标识(account_identifier)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_identifier")
public class AccountIdentifier extends Model<AccountIdentifier> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 用户名ID
     */
    private Long accountId;
    /**
     * 识别标识:身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号；
     */
    private String identifier;
    /**
     * 授权凭证【CREDENTIAL】：站内账号是密码、第三方登录是Token；
     */
    private String credential;
    /**
     * 登录类型：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；
     */
    private String loginType;
    /**
     * 关联的用户Id
     */
    private String userId;
    /**
     * 是否被锁定，否认false
     */
    private Integer locked;
    /**
     * 时间戳
     */
    private Date st;

}