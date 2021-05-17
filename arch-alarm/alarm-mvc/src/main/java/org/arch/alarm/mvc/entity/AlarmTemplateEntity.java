package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 履约预警通知模板 定义不同场景，不同的通知模式（direct，fanout，topic等），不同的消息内容，不同的通知通道。如需要针对骑手提货异常发送微信消息提醒，选择微信通知，选择通知模板，编辑或使用规则模板内容或使用自定义通知内容完成配置，当到达业务预值时，触发该通知，不仅可以同时通知到骑手，还可以通知主管，通知仓店等..... 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_template")
public class AlarmTemplateEntity implements Serializable {

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
     * 预警名称
     */
    private String regName;

    /**
     * 模式值{1:点对点，2：广播，3:订阅}
     */
    private String modeVal;

    /**
     * 模式名
     */
    private String modeName;

    /**
     * 通知内容，可取自模板或自定义， 用regKey，regVal，regName，currVal 填充
     */
    private String content;

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
