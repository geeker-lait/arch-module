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
 * 营销-自定义动态属性值(market_attrval)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_attrval")
public class MarketAttrval extends Model<MarketAttrval> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 属性ID
     */
    private Long attrNameId;
    /**
     * 属性值
     */
    private String attrVal;
    /**
     * 手动输入属性值（attrVal）的自定义值
     */
    private String definedVal;
    /**
     * 时间戳
     */
    private Date st;

}