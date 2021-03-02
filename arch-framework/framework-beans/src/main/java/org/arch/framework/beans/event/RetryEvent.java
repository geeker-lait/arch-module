package org.arch.framework.beans.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * 重试事件, 适用不关心返回值情况.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.2 13:55
 */
public class RetryEvent extends ApplicationEvent {

    private static final long serialVersionUID = -865464219842391509L;

    private final Class<?> retryClz;
    private final Method retryMethod;
    private final Object[] retryArgs;
    private final String traceId;
    /**
     * 重试次数
     */
    private final LongAdder retryNo;
    /**
     * 重试间隔.
     */
    private int retryInterval = 1500;
    /**
     * 重试间隔单位.
     */
    private TimeUnit retryTimeUnit = TimeUnit.MILLISECONDS;

    /**
     *
     * @param retryObj      重试对象, 通过 {@link ApplicationEvent#getSource()} 获取
     * @param traceId       调用链路追踪 id
     * @param retryClz      重试类型
     * @param retryMethod   重试方法
     * @param retryArgs     重试参数
     */
    public RetryEvent(@NonNull Object retryObj, @Nullable String traceId,
                      @NonNull Class<?> retryClz, @NonNull Method retryMethod,
                      @Nullable Object... retryArgs) {
        super(retryObj);
        this.traceId = traceId;
        this.retryClz = retryClz;
        this.retryMethod = retryMethod;
        this.retryArgs = retryArgs;
        this.retryNo = new LongAdder();
    }

    public Class<?> getRetryClz() {
        return retryClz;
    }

    public Method getRetryMethod() {
        return retryMethod;
    }

    public Object[] getRetryArgs() {
        return retryArgs;
    }

    public String getTraceId() {
        return traceId;
    }

    public void addRetryNo() {
        retryNo.increment();
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    public int getRetryNo() {
        return retryNo.intValue();
    }

    public TimeUnit getRetryTimeUnit() {
        return retryTimeUnit;
    }

    public void setRetryTimeUnit(TimeUnit retryTimeUnit) {
        this.retryTimeUnit = retryTimeUnit;
    }

    @Override
    public String toString() {
        return "RetryEvent{" +
                "retryClz=" + retryClz +
                ", retryMethod=" + retryMethod +
                ", retryArgs=" + Arrays.toString(retryArgs) +
                ", traceId='" + traceId + '\'' +
                ", retryNo=" + retryNo +
                ", retryInterval=" + retryInterval +
                '}';
    }
}
