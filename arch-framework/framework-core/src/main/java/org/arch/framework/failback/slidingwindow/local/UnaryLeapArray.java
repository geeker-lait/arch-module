package org.arch.framework.failback.slidingwindow.local;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-03-25
 */
public class UnaryLeapArray extends LeapArray<LongAdder> {

    public UnaryLeapArray(int sampleCount, int intervalInMs) {
        super(sampleCount, intervalInMs);
    }

    @Override
    public LongAdder newEmptyBucket() {
        return new LongAdder();
    }

    @Override
    protected WindowWrap<LongAdder> resetWindowTo(WindowWrap<LongAdder> windowWrap, long startTime) {
        windowWrap.value().reset();
        return windowWrap.resetTo(startTime);
    }
}
