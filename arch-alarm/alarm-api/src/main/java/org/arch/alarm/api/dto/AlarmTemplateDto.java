package org.arch.alarm.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/8/2021 5:37 PM
 */
@Data
public class AlarmTemplateDto implements Serializable {
    /**
     * 主键
     */
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
