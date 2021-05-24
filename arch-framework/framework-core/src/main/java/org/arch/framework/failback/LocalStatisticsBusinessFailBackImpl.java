package org.arch.framework.failback;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.failback.slidingwindow.local.LeapArray;
import org.arch.framework.failback.slidingwindow.local.UnaryLeapArray;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    private static final Map<String, CircuitBreakerInfo> LOCAL_CIRCUIT_BREAKER_MAP =  new ConcurrentHashMap<>();


    private static final int interval = 1_000 * 60;


    @Override
    boolean preExecute(BusinessFailBackConfig config, BusinessFailBackConfig.Channel currentChannel) {
        String statisticsKey = getStatisticsKey(config, currentChannel);
        Boolean lastResult = true;
        RelationEnum relation = currentChannel.getRelation();

        for (BusinessFailBackConfig.Rule rule : currentChannel.getRules()) {
            Integer duration = rule.getDuration() * 60 * 1000;
            String key = statisticsKey + ":" + rule.getRuleCode();
            if (LOCAL_STATISTICS_MAP.containsKey(key)) {
                CircuitBreakerInfo circuitBreakerInfo = LOCAL_CIRCUIT_BREAKER_MAP.get(statisticsKey);
                if (circuitBreakerInfo != null && circuitBreakerInfo.getCircuitBreakerEndTime().getTime() > System.currentTimeMillis()) {
                    log.info("在指定熔断时间范围内，直接熔断 key:{}", key);
                    return false;
                }
                if (circuitBreakerInfo != null && circuitBreakerInfo.getCircuitBreakerEndTime().getTime() <= System.currentTimeMillis()) {
                    LOCAL_STATISTICS_MAP.remove(key);
                }
            } else {
                // 新增
                LOCAL_STATISTICS_MAP.put(key, new UnaryLeapArray(duration / interval, interval));
                lastResult = lastResult == null ? true : RelationEnum.OR.equals(relation) ? true : (lastResult & true);
            }
        }
        return lastResult;
    }

    @Override
    boolean afterExecute(BusinessFailBackConfig config, BusinessFailBackConfig.Channel currentChannel, boolean failBack, boolean executeResult) {
        String statisticsKey = getStatisticsKey(config, currentChannel);
        RelationEnum relation = currentChannel.getRelation();
        Boolean isFailBack = null;

        for (BusinessFailBackConfig.Rule rule : currentChannel.getRules()) {
            String key = statisticsKey + ":" + rule.getRuleCode();
            if (!LOCAL_STATISTICS_MAP.containsKey(key)) {
                continue;
            }
            RecordEnum writeRecordEnum = rule.getWriteRecordEnum();
            boolean needAdd = RecordEnum.FAIL_BACK.equals(writeRecordEnum) && failBack ? true :
                    RecordEnum.FAIL.equals(writeRecordEnum) && !failBack && !executeResult ? true :
                            RecordEnum.SUCCESS.equals(writeRecordEnum) && !failBack && executeResult ? true : false;
            LeapArray<LongAdder> longAdderLeapArray = LOCAL_STATISTICS_MAP.get(key);
            if (needAdd) {
                System.out.println(longAdderLeapArray.currentWindow().value() + "-----------------");
                longAdderLeapArray.currentWindow().value().add(1);
            }
            boolean addResult = longAdderLeapArray.getAllWindowWrap().stream().mapToLong(windows -> windows.value().longValue()).sum() > rule.getFailBackNum();
            if (isFailBack == null) {
                isFailBack = addResult && RelationEnum.OR.equals(relation) ? true :
                        addResult && RelationEnum.AND.equals(relation) ? true : false;
                continue;
            }
            // 超过规则的数量 并且是 or
            if (addResult && RelationEnum.OR.equals(relation)) {
                isFailBack = true;
            }
            if (addResult && RelationEnum.AND.equals(relation) && isFailBack) {
                isFailBack = true && isFailBack;
            }
        }
        if (isFailBack != null && isFailBack) {
            CircuitBreakerInfo circuitBreakerInfo = new CircuitBreakerInfo();
            circuitBreakerInfo.setAppCode(config.getAppCode());
            circuitBreakerInfo.setChannelCode(currentChannel.getChannelCode());
            circuitBreakerInfo.setCircuitBreakerNum(currentChannel.getCircuitBreakerTimeSpan());
            circuitBreakerInfo.setCircuitBreakerStartTime(new Date());
            circuitBreakerInfo.setCircuitBreakerEndTime(DateTime.now().plusMinutes(currentChannel.getCircuitBreakerTimeSpan().intValue()).toDate());
            LOCAL_CIRCUIT_BREAKER_MAP.put(statisticsKey, circuitBreakerInfo);
            log.info("通道熔断 key:{}", statisticsKey);
        }
        return true;
    }
}
