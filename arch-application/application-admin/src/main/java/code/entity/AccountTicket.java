package code.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 账号-券(account_ticket)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_ticket")
public class AccountTicket extends Model<AccountTicket> implements Serializable {
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
     * 券类型：打折，优惠，抵用....
     */
    private Integer ticketTyp;
    /**
     * 券号
     */
    private String ticketNo;
    /**
     * 券值
     */
    private Integer ticketVal;

}