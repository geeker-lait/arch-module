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
 * 账号操作记录(account_operate_log)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_operate_log")
public class AccountOperateLog extends Model<AccountOperateLog> implements Serializable {
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
     * 操作的服务
     */
    private String operateService;
    /**
     * 操作类型()
     */
    private Integer operateTable;
    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * 记录的值json
     */
    private String recordVal;

}