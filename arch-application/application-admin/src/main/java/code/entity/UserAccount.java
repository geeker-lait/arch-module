package code.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户-账号信息(user_account)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_account")
public class UserAccount extends Model<UserAccount> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 账号ID
     */
    private String accountId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 账号关联的手机号
     */
    private Long phoneNum;
    /**
     * 顺序
     */
    private Integer sorted;
    /**
     * 时间戳
     */
    private Date st;

}