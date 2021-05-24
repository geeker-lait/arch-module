package org.arch.framework.failback.slidingwindow.local;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-03-25
 */
public abstract class LeapArray<T> {

    /**
     * 时间窗口的长度-总时长 单位 毫秒
     */
    protected int windowLengthInMs;
    /**
     * 采样窗口的个数
     */
    protected int sampleCount;
    /**
     * 以毫秒为单位的时间间隔
     */
    protected int intervalInMs;
    /**
     * 采样的时间窗口数组
     */
    protected AtomicReferenceArray<WindowWrap<T>> array;

    /**
     * 用于当前桶废弃时
     */
    private final ReentrantLock updateLock = new ReentrantLock();

    public LeapArray(int sampleCount, int intervalInMs) {
        this.windowLengthInMs = intervalInMs / sampleCount;
        this.sampleCount = sampleCount;
        this.intervalInMs = intervalInMs;
        this.array = new AtomicReferenceArray<WindowWrap<T>>(sampleCount);
    }

    public abstract T newEmptyBucket();

    protected abstract WindowWrap<T> resetWindowTo(WindowWrap<T> windowWrap, long startTime);


    public int calculateTimeIdx(long timeMillis) {
        long timeId = timeMillis / windowLengthInMs;
        return (int) (timeId % array.length());
    }

    public long calculateWindowStart(long timeMillis) {
        return timeMillis - timeMillis % windowLengthInMs;
    }

    public WindowWrap<T> currentWindow() {
        return currentWindow(System.currentTimeMillis());
    }

    /**
     * 获取当前Window
     * @param timeMillis
     * @return
     */
    public WindowWrap<T> currentWindow(long timeMillis) {
        if (timeMillis < 0) {
            return null;
        }

        int idx = calculateTimeIdx(timeMillis);

        System.out.println("idx=" + idx);

        long windowStart = calculateWindowStart(timeMillis);

        while (true) {
            WindowWrap<T> old = array.get(idx);

            if (old == null) {
                WindowWrap<T> window = new WindowWrap<>(windowLengthInMs, windowStart, newEmptyBucket());
                if (array.compareAndSet(idx, null, window)) {
                    return window;
                } else {
                    //礼让。别的线程设置成功了
                    Thread.yield();
                }
            } else if (windowStart == old.windowStart()) {
                return old;
            } else if (windowStart > old.windowStart()) {
                if (updateLock.tryLock()) {
                    try {
                        return resetWindowTo(old, windowStart);
                    } finally {
                        updateLock.unlock();
                    }
                } else {
                    Thread.yield();
                }
            } else if (windowStart < old.windowStart()) {
                return new WindowWrap<>(windowLengthInMs, windowStart, newEmptyBucket());
            }
        }

    }


    protected boolean isWindowDeprecated(WindowWrap<T> windowWrap) {
        return System.currentTimeMillis() - windowWrap.windowStart() > intervalInMs;
    }

    /**
     * 扩容时判断 窗口是否已过期
     * @param windowWrap
     * @return
     */
    protected boolean isWindowDeprecatedByResize(WindowWrap<T> windowWrap) {
        return System.currentTimeMillis() - windowWrap.windowStart() > intervalInMs * sampleCount;
    }

    public List<T> values() {
        int size = array.length();
        List<T> result = new ArrayList<T>(size);

        for (int i = 0; i < size; i++) {
            WindowWrap<T> windowWrap = array.get(i);
            if (windowWrap == null || isWindowDeprecated(windowWrap)) {
                continue;
            }
            result.add(windowWrap.value());
        }
        return result;
    }

    /**
     * 扩容
     * 创建后 时间间隔不能修改，只能修改容量 也就是滑动窗口时长,每个的时间间隔不能修改
     */
    public void resize(int sampleCount) {
        while (true) {
            if (updateLock.tryLock()) {
                try {
                    if (sampleCount == this.sampleCount) {
                        // 新的容量相等不需要 扩容
                        return;
                    } else if (sampleCount > this.sampleCount) {
                        this.windowLengthInMs = intervalInMs / sampleCount;
                        this.sampleCount = sampleCount;
                        List<WindowWrap<T>> oldWindowWraps = getAllWindowWrap();
                        array = new AtomicReferenceArray<>(sampleCount);
                        // 扩容
                        for (int i = 0; i < oldWindowWraps.size(); i++) {
                            WindowWrap<T> windowWrap = oldWindowWraps.get(i);
                            WindowWrap<T> window = new WindowWrap<>(windowLengthInMs, windowWrap.windowStart(), newEmptyBucket());
                            window.setValue(windowWrap.value());
                            int index = calculateTimeIdx(windowWrap.windowStart());
                            array.set(index, window);
                        }
                        System.out.println("扩容完毕 length:" + array.length());
                    } else {
                        this.windowLengthInMs = intervalInMs / sampleCount;
                        this.sampleCount = sampleCount;
                        List<WindowWrap<T>> oldWindowWraps = getAllWindowWrap();
                        array = new AtomicReferenceArray<>(sampleCount);
                        // 缩容
                        for (int i = 0; i < oldWindowWraps.size(); i++) {
                            WindowWrap<T> windowWrap = oldWindowWraps.get(i);
                            if (windowWrap == null || isWindowDeprecatedByResize(windowWrap)) {
                                continue;
                            }
                            WindowWrap<T> window = new WindowWrap<>(windowLengthInMs, windowWrap.windowStart(), newEmptyBucket());
                            window.setValue(windowWrap.value());
                            int index = calculateTimeIdx(windowWrap.windowStart());
                            array.set(index, window);
                        }
                        System.out.println("缩容完毕 length:" + array.length());
                    }
                    return;
                } finally {
                    updateLock.unlock();
                }
            } else {
                Thread.yield();
            }
        }
    }

    /**
     * 获取当前滑动窗口所有的 WindowWrap
     * @return
     */
    public List<WindowWrap<T>> getAllWindowWrap() {
        int size = array.length();
        List<WindowWrap<T>> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            WindowWrap<T> windowWrap = array.get(i);
            if (windowWrap == null || isWindowDeprecated(windowWrap)) {
                continue;
            }
            result.add(windowWrap);
        }
        return result;
    }
}
