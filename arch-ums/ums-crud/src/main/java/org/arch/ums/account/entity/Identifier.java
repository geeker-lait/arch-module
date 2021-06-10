package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;
import org.arch.framework.encrypt.EncryptClass;
import org.arch.framework.encrypt.EncryptField;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户-标识(Identifier) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 22:37:30
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_identifier")
@EncryptClass
public class Identifier extends CrudEntity<Identifier> {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-标识 ID
     */
    @TableId(value = "`id`", type = IdType.INPUT)
    private Long id;

    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    @TableField(value = "`aid`")
    private Long aid;

    /**
     * 识别标识:身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号；
     */
    @TableField(value = "`identifier`")
    private String identifier;

    /**
     * 授权凭证【CREDENTIAL】：站内账号是密码、第三方登录是Token；
     */
    @TableField(value = "`credential`")
    @EncryptField(encryptType = "bcrypt")
    private String credential;

    /**
     * 用户角色:ROLE_xxx 与 租户id: TENANT_XXX
     */
    @TableField(value = "`authorities`")
    private String authorities;

    /**
     * 登录类型：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；
     */
    @TableField(value = "`login_type`")
    private Integer loginType;

    /**
     * 租户 id
     */
    @TableField(value = "`tenant_id`")
    private Integer tenantId;

    /**
     * 应用 id
     */
    @TableField(value = "`app_id`")
    private Integer appId;

    /**
     * 店铺 id
     */
    @TableField(value = "`store_id`")
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    @TableField(value = "`rev`")
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    @TableField(value = "`dt`")
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    @TableField(value = "`deleted`")
    private Boolean deleted;


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
