package code.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 账号-岗位(account_post)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_post")
public class AccountPost extends Model<AccountPost> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 父id
     */
    private Long postPid;
    /**
     * 岗位名
     */
    private String postName;
    /**
     * 岗位code
     */
    private String postCode;
    /**
     * icon
     */
    private String postIcon;
    /**
     * 薪资
     */
    private BigDecimal salary;

}