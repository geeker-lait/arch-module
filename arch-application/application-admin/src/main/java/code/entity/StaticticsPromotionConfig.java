package code.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 统计-统计配置(statictics_promotion_config)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("statictics_promotion_config")
public class StaticticsPromotionConfig extends Model<StaticticsPromotionConfig> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 源（推广渠道标识源）推广渠道/渠道来源
     */
    private String source;
    /**
     * 资源的标识ID（产品，信息等）
     */
    private Long resourceId;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 推广价格
     */
    private BigDecimal promotionPrice;
    /**
     * 推广数量
     */
    private BigDecimal promotionCount;
    /**
     * 扣量百分比
     */
    private BigDecimal deductPercent;
    /**
     * 推广商名称
     */
    private String promotionName;
    /**
     * 推广方式:uv,cpa,cps
     */
    private Integer promotionTyp;
    /**
     * 引流推广页地址
     */
    private String promotionUrl;
    /**
     * 开放统计看板的用户名
     */
    private String userName;
    /**
     * 开放统计看板的密码
     */
    private String pwd;
    /**
     * 时间戳
     */
    private Date st;

}