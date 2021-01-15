package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 账号名(account_name)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_name")
public class AccountName extends Model<AccountName> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号名ID
     */
    @TableId
	private Long id;
    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    private Long accountId;
    /**
     * 用户昵称可随机生成
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 来源, 推广统计用
     */
    private String source;

}