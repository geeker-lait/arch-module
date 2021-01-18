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
 * 营销主题(market_master)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_master")
public class MarketMaster extends Model<MarketMaster> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 营销名称ID
     */
    private Long marketNameId;
    /**
     * 营销名称
     */
    private String marketName;
    /**
     * 动态创建营销对应的表
     */
    private String marketTable;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 店铺ID
     */
    private Long storeId;
    /**
     * 应用ID
     */
    private Long appId;
    /**
     * 账号ID
     */
    private Long accountId;
    /**
     * 使用说明
     */
    private String descr;
    /**
     * h5链接
     */
    private String h5;
    /**
     * 有效期/开始时间
     */
    private Date startTime;
    /**
     * 失效期/结束时间
     */
    private Date endTime;
    /**
     * 时间戳
     */
    private Date st;

}