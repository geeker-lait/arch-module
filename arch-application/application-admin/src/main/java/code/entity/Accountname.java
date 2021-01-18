package code.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 账号-名称(accountname)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("accountname")
public class Accountname extends Model<Accountname> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
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
     * 来源，推广统计用
     */
    private String source;

}