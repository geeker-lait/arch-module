package org.arch.framework.failback;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 记录熔断信息
 * @author junboXiang
 * @version V1.0
 * 2021-04-08
 */
@Data
@Accessors(chain = true)
public class CircuitBreakerInfo {
    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 租户对应的app(租户与app一对多) 与租户对应
     */
    private String appCode;

    /**
     * 通道Code
     */
    private String channelCode;

    /**
     * 熔断开始时间
     */
    private Date circuitBreakerStartTime;

    /**
     * 熔断结束时间
     */
    private Date circuitBreakerEndTime;

    /**
     * 熔断的时间跨度
     */
    private Long circuitBreakerTimeSpan;

    /**
     * 熔断的阈值
     */
    private Long circuitBreakerNum;



}
