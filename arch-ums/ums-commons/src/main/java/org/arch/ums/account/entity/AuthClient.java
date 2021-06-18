package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;
import org.arch.framework.encrypt.EncryptField;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 授权客户端(AuthClient) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:25:26
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_auth_client")
public class AuthClient extends CrudEntity<AuthClient> {
    private static final long serialVersionUID = 1L;

    /**
     * 授权客户端ID
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 客户端 ID
     */
    @TableField(value = "`client_id`")
    private String clientId;

    /**
     * 客户端 secret
     */
    @TableField(value = "`client_secret`")
    @EncryptField(encryptType = "BCRYPT")
    private String clientSecret;

    /**
     * scope id 列表, 如: openid/userinfo/token/code/资源服务器标识等
     */
    @TableField(value = "`scopes`")
    private String scopes;

    /**
     * 角色 id 列表, 通过逗号分割
     */
    @TableField(value = "`role_ids`")
    private String roleIds;

    /**
     * 客户端类型: web, 安卓, ios, 小程序…
     */
    @TableField(value = "`client_type`")
    private Integer clientType;

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
