package org.arch.framework.failback;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.failback.slidingwindow.local.LeapArray;
import org.arch.framework.failback.slidingwindow.local.UnaryLeapArray;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 本地统计的降级调用判断
 * @author junboXiang
 * @version V1.0
 * 2021-04-05
 */
@Slf4j
@Service
public class LocalStatisticsBusinessFailBackImpl extends BusinessFailBack {

    private static final Map<String, LeapArray<LongAdder>> LOCAL_STATISTICS_MAP = new ConcurrentHashMap<>();


    private static final int interval = 1_000 * 60;


    @Override
    boolean preExecute(BusinessFailBackConfig config, BusinessFailBackConfig.Channel currentChannel) {
        String statisticsKey = getStatisticsKey(config, currentChannel);
        Boolean lastResult = null;
        RelationEnum relation = currentChannel.getRelation();
        for (BusinessFailBackConfig.Rule rule : currentChannel.getRules()) {
            if (lastResult != null && RelationEnum.OR.equals(relation) && lastResult) {
                // or 操作有一个 true 直接返回
                return lastResult;
            }
            Integer duration = rule.getDuration() * 1_000 * 60;
            String key = statisticsKey + ":" + rule.getRuleCode();
            if (LOCAL_STATISTICS_MAP.containsKey(key)) {
                LeapArray<LongAdder> longAdderLeapArray = LOCAL_STATISTICS_MAP.get(key);
                // 是否需要扩容
                longAdderLeapArray.resize(duration / interval);
                long sum = longAdderLeapArray.getAllWindowWrap().stream().mapToLong(windows -> windows.value().longValue()).sum();
                lastResult = lastResult == null ? sum >= rule.getFailNum() : RelationEnum.OR.equals(relation) ? (lastResult | sum >= rule.getFailNum()) : (lastResult & sum >= rule.getFailNum());
            } else {
                // 新增
                // 转换成秒的时间间隔

                LOCAL_STATISTICS_MAP.put(key, new UnaryLeapArray(duration / interval, duration));
                lastResult = lastResult == null ? true : RelationEnum.OR.equals(relation) ? true : (lastResult & true);
            }
        }
        return lastResult;
    }

    @Override
    boolean afterExecute(BusinessFailBackConfig config, BusinessFailBackConfig.Channel currentChannel, boolean failBack, boolean executeResult) {
        if (executeResult || failBack) {
            return true;
        }
        String statisticsKey = getStatisticsKey(config, currentChannel);
        for (BusinessFailBackConfig.Rule rule : currentChannel.getRules()) {
            String key = statisticsKey + ":" + rule.getRuleCode();
            if (LOCAL_STATISTICS_MAP.containsKey(key)) {
                LOCAL_STATISTICS_MAP.get(key).currentWindow().value().add(1);
            }
        }
        return true;
    }
}
