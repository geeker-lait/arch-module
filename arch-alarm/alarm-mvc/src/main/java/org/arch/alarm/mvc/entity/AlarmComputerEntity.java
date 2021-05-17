package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 预警规则计算bean 预警规则计算bean，spring启动时会注册 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:53 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_computer")
public class AlarmComputerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 计算器名称，对应一个规则计算bean，spring启动时注册进来（唯一）
     */
    private String computerName;

    /**
     * 计算器支持的参数列表，自定义时必须知道参数
     */
    private String paramsJson;

    /**
     * 描述描述
     */
    private String computerDescr;

    /**
     * 版本
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
