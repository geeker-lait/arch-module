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
 * 营销参与者(market_actor)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_actor")
public class MarketActor extends Model<MarketActor> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 营销ID
     */
    private Long marketId;
    /**
     * 营销类型
     */
    private Integer marketTyp;
    /**
     * 账号id
     */
    private Long accountId;
    /**
     * 营销规则
     */
    private String ruleJson;
    /**
     * 时间戳
     */
    private Date st;

}