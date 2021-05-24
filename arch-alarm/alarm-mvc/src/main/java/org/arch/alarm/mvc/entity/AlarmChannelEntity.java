package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 履约预警通道维度 信息爆炸，每人每天平均要收到2条以上垃圾短信，一些重要消息很可能被遗漏，或不被引起关注。因此我们需要更人性化的让用户可以选择以何种方式发送提醒与预警，竟而更精准有效的通知到他们，如邮件、系统消息、短信、微信等。 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_channel")
public class AlarmChannelEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 仓店号
     */
    private String storeNo;

    /**
     * 通道码，msg，weixin，email，sys
     */
    private String channelCode;

    /**
     * 通道值，1，2，3....
     */
    private Integer channelVal;

    /**
     * 通道名称
     */
    private String channelName;

    /**
     * 通道url
     */
    private String channelUrl;

    /**
     * 回调地址
     */
    private String callbackUrl;

    /**
     * 签名
     */
    private String sign;

    /**
     * 应用id或应用key
     */
    private String appKey;

    /**
     * 密钥
     */
    private String secrtKey;

    /**
     * 扩展属性
     */
    private String extJsonp;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 租户id
     */
    private Long tenantId;


}
