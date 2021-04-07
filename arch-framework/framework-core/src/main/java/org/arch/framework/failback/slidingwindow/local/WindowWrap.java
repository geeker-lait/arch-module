package org.arch.framework.failback.slidingwindow.local;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-03-25
 */
public class WindowWrap<T> {


    /**
     * 单个存储桶的窗口的时间长度
     */
    private final long windowLengthInMs;

    /**
     * 单个时间窗口的开始时间(毫秒值)
     */
    private long windowStart;

    /**
     * 时间窗口存储的数据
     */
    private T value;

    public WindowWrap(long windowLengthInMs, long windowStart, T value) {
        this.windowLengthInMs = windowLengthInMs;
        this.windowStart = windowStart;
        this.value = value;
    }

    public long windowLength() {
        return windowLengthInMs;
    }

    public long windowStart() {
        return windowStart;
    }

    public T value() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public WindowWrap<T> resetTo(long startTime){
        this.windowStart = startTime;
        return this;
    }

    @Override
    public String toString() {
        return "WindowWrap{" +
                "windowLengthInMs=" + windowLengthInMs +
                ", windowStart=" + new DateTime(windowStart).toString(DateTimeFormat.forPattern("HH:mm:ss")) +
                ", value=" + value +
                '}';
    }
}