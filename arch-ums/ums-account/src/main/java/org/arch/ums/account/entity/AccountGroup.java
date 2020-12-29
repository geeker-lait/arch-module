package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 账号-组织机构(account_group)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_group")
public class AccountGroup extends Model<AccountGroup> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-权限ID
     */
    @TableId
	private Long id;
    /**
     * 父ID
     */
    private Long groupPid;
    /**
     * 组code
     */
    private String groupCode;
    /**
     * 组织架构名
     */
    private String groupName;
    /**
     * 组织架构ICON
     */
    private String groupIcon;
    /**
     * 排序
     */
    private Integer sorted;

}