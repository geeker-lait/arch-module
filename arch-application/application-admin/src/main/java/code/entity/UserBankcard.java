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
 * 用户-银行卡信息(user_bankcard)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_bankcard")
public class UserBankcard extends Model<UserBankcard> implements Serializable {
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
     * 卡号
     */
    private String cardNo;
    /**
     * 卡bin 如：6221，6222
     */
    private String cardBin;
    /**
     * 卡code 如：ICBC,ABC
     */
    private String cardCode;
    /**
     * 卡cvv
     */
    private String cvv;
    /**
     * 卡类型：0:储蓄卡，1：借记卡
     */
    private Integer cardTyp;
    /**
     * 顺序
     */
    private Integer sorted;
    /**
     * 有效期
     */
    private Date validity;
    /**
     * 时间戳
     */
    private Date st;

}