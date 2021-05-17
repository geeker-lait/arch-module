package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 预警规则param 预警规则计算参数 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_params")
public class AlarmParamsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 规则id
     */
    private Long regId;

    /**
     * 冗余规则名称，展示用
     */
    private String regName;

    /**
     * 字典id冗余处理，查找用
     */
    private Long dicId;

    /**
     * 库表,值如db.tb 确定唯一
     */
    private String dbtb;

    /**
     * 字段名，展示用
     */
    private String filedName;

    /**
     * 字段码
     */
    private String filedCode;

    /**
     * 字段类型
     */
    private String filedTyp;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 租户id
     */
    private Long tenantId;


}
