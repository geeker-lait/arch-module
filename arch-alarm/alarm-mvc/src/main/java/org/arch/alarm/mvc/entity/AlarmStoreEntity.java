package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 履约告警仓店实体 通过仓店配置，将storeNo 和catId建立关联关系，开启对仓店的监控 同时可选择开启监控那些指标regJson 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_store")
public class AlarmStoreEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 仓店编号
     */
    private String storeNo;

    /**
     * 预警类目id
     */
    private Long catId;

    /**
     * 预警规则json对象，由ofs_alarm_reg对象配置相应的键值对，这里可以用另一张表来记录
     */
    private String regJson;

    /**
     * 业务线，用来筛选系统如履约|wms|其他
     */
    private String bizLine;

    /**
     * 版本号
     */
    private Integer ver;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 租户id
     */
    private Long tenantId;


}
