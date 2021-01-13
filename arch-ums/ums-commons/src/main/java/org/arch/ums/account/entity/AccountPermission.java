package org.arch.ums.account.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 账号-权限(account_permission)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_permission")
public class AccountPermission extends Model<AccountPermission> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-菜单ID
     */
    @TableId
	private Long id;
    /**
     * 权限码(与RequestMethod对应)list(GET)/add(POST)/edit(PUT)/delete(DELETE)/..
     */
    private String permissionCode;
    /**
     * 权限名称
     */
    private String permissionName;
    /**
     * 权限值
     */
    private String permissionVal;
    /**
     * uri
     */
    private String permissionUri;
    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    private Integer permissionTyp;
    /**
     * 排序
     */
    private Integer sorted;

}