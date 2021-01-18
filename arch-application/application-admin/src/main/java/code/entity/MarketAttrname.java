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
 * 营销-自定义动态属性(market_attrname)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_attrname")
public class MarketAttrname extends Model<MarketAttrname> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 营销ID
     */
    private Long nameId;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * 字段名
     */
    private String filedName;
    /**
     * 字段类型：string，int，long，decimal，double
     */
    private String filedTyp;
    /**
     * 属性选择的类型:0->唯一，1->单选，2->多选
     */
    private Integer selectTyp;
    /**
     * 属性录入方式:0->手工录入，1->从列表中选取
     */
    private Integer inputTyp;
    /**
     * 数字类型参数的单位，非数字类型可以为空
     */
    private String unit;
    /**
     * 时间戳
     */
    private Date st;

}