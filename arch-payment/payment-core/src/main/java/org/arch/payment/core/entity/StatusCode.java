package org.arch.payment.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * <p>
 *
 * </p>
 *
 * @since 2020-04-17
 */
@Data
@Accessors(chain = true)
@TableName("pay_status_code")
public class StatusCode {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    @TableField("channel_code")
    private String channelCode;

    @TableField("channel_id")
    private String channelId;

    @TableField("channel_state_code")
    private String channelStateCode;

    @TableField("channel_state_descr")
    private String channelStateDescr;

    @TableField("uni_state_code")
    private String uniStateCode;

    @TableField("uni_state_descr")
    private String uniStateDescr;


}
