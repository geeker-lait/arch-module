package org.arch.project.alarm.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@Builder
public class AlarmRegEntity {
    //主键
    private Long id;
    //类目id
    private Long catid;
    //对应ofs_alarm_computer表中单一个计算器
    private Long computerId;
    //规则名称
    private String regName;
    //条件或条件表达式
    private String regKey;
    //条件或条件表达式
    private String condition;
    //规则默认值，参考值
    private String regVal;
    /**
     * 规则值来源，可以是一个api接口，一条sql语句
     * 如：
     * http://xxx/getDeliveryTime……
     * sql://192.168.0.1@db@user:pwd/select * from tb……
     */
    private String regDatasource;
    //规则模板通知内容中的占位符
    private String placeholder;
    //规则对描述
    private String descr;
    //创建时间
    private Date ctime;

}
