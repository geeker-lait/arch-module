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
 * 统计-行为统计(statistics_master)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("statistics_master")
public class StatisticsMaster extends Model<StatisticsMaster> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 源（推广渠道标识源）推广渠道/渠道来源
     */
    private String source;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 资源的标识ID（产品，信息等）
     */
    private Long resourceId;
    /**
     * 资源类型产品/页面/信息等
     */
    private Integer resourceTyp;
    /**
     * 行为类型uv/pv/cpc/cpa/cps
     */
    private Integer behaviorTyp;
    /**
     * 设备ID
     */
    private String deviceId;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 系统类型ios/android/pc
     */
    private String osTyp;
    /**
     * 系统版本
     */
    private String osVersion;
    /**
     * 设备MAC
     */
    private String mac;
    /**
     * 应用Id
     */
    private String appId;
    /**
     * 账号ID
     */
    private Long accountId;
    /**
     * 消耗/成本
     */
    private BigDecimal cost;
    /**
     * 时间戳
     */
    private Date st;

}