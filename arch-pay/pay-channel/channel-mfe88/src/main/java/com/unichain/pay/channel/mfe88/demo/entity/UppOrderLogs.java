package com.unichain.pay.channel.mfe88.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 类名称：
 * UppOrderLogs 实体
 * UPP_ORDER_LOGS 表名
 * 创建时间：2018-12-04
 */
@Data
public class UppOrderLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String TABLE_NAME = "UPP_ORDER_LOGS";

    /**
     * ID-ID
     */
    private Long id;
    /**
     * CREATE_TIME-创建时间
     */
//	@JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;
    /**
     * ORDER_TIME-订单时间
     */
//	@JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    private Date orderTime;
    /**
     * LOG_CONTENT-日志内容
     */
    private String logContent;
    /**
     * USER_ID-用户id
     */
    private String userId;
    /**
     * LOG_TYPE-日志类型 下单\查询\结果处理\通知\
     */
    private String logType;
    private String orderNo;

    private String startCreateTime;
    private String endCreateTime;

}

