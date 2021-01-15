package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 账号-角色(account_role)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_role")
public class AccountRole extends Model<AccountRole> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号角色ID
     */
    @TableId
	private Long id;
    /**
     * 租户 id
     */
    private String tenantId;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色icon
     */
    private String icon;
    /**
     * 顺序
     */
    private Integer sorted;

}