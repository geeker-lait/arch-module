package org.arch.ums.account.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 账号操作记录(account_operat_log)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_operat_log")
public class AccountOperatLog extends Model<AccountOperatLog> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 用户id
     */
    private Long accountId;
    /**
     * 操作类型()
     */
    private Integer operatorTyp;
    /**
     * 操作时间
     */
    private Date operatorTime;
    /**
     * 记录的值json
     */
    private String recordVal;

}