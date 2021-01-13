package org.arch.ums.account.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 账号-关系(account_relationship)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_relationship")
public class AccountRelationship extends Model<AccountRelationship> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private Long id;
    /**
     * 父节点ID（数据库自增）
     */
    private Long pid;
    /**
     * 组
     */
    private Integer org;
    /**
     * 深度
     */
    private Integer deep;
    /**
     * 顺序
     */
    private Integer seq;
    /**
     * 推荐人ID
     */
    private Long fromUserId;
    /**
     * 推荐人姓名
     */
    private String fromUserName;
    /**
     * 推荐人手机
     */
    private String fromUserPhone;
    /**
     * 账号ID
     */
    private String toUserId;
    /**
     * 用户名
     */
    private String toUserName;
    /**
     * 用户手机
     */
    private String toUserPhone;

}