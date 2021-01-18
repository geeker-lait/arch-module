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
 * 营销-名称(market_name)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_name")
public class MarketName extends Model<MarketName> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 父ID
     */
    private Long pid;
    /**
     * 分类如：1:获客拉新、2:下单转化、3:提高客单、4:复购留存、5:....(数据字典获取)
     */
    private Integer marketTyp;
    /**
     * 名称
     */
    private String marketName;
    /**
     * 营销英文名
     */
    private String marketCode;
    /**
     * 图标
     */
    private String icon;
    /**
     * 层级
     */
    private Integer tier;
    /**
     * 排序
     */
    private Integer sorted;
    /**
     * 描述
     */
    private String descr;
    /**
     * 时间戳
     */
    private Date st;

}