package org.arch.auth.sso.properties;

import lombok.Getter;
import lombok.Setter;
import org.arch.auth.sso.listener.RetryListener;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * 针对 {@link RetryListener} 的重试属性设置.
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.3 18:05
 */
@ConfigurationProperties(prefix = "arch.retry")
@Getter
@Setter
public class RetryProperties {
    /**
     * 重试次数, 默认: 2
     */
    private Integer maxAttempts;

    /**
     * 重试延迟时间, 默认: 1500 毫秒
     */
    private Integer delay;

    /**
     * 重试时间单位, 默认: 毫秒
     */
    private TimeUnit timeUnit = TimeUnit.MICROSECONDS;
}
