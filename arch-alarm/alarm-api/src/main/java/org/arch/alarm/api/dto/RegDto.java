package org.arch.alarm.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/4/2021 12:00 AM
 */
@Data
public class RegDto implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 类目id
     */
    private Long catid;

    /**
     * 规则名称
     */
    private String regName;

    /**
     * 规则健，key唯一
     */
    private String regKey;

    /**
     * 规则默认值，参考值
     */
    private String regVal;

    /**
     * 规则参考值来源，可以是一个api接口，一条sql语句 如： http://xxx/getDeliveryTime…… sql://192.168.0.1@db@user:pwd/select * from tb……
     */
    private String regDatasource;

    /**
     * 规则对描述
     */
    private String descr;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 租户id
     */
    private Long tenantId;

}
