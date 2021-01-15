package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 账号-资源类目(account_category)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_category")
public class AccountCategory extends Model<AccountCategory> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源类目ID
     */
    @TableId
	private Long id;
    /**
     * 父节点ID
     */
    private Long pid;
    /**
     * 租户 ID
     */
    private String tenantId;
    /**
     * 资源类目名
     */
    private String categoryName;
    /**
     * 顺序
     */
    private Integer sorted;

}