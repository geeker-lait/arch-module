package org.arch.framework.failback;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 业务降级执行接口类
 * @author junboXiang
 * @version V1.0
 * 2021-02-26
 */
@Slf4j
public abstract class BusinessFailBack {


    /**
     *  业务降级执行 抽象demo级别，仅供参考
     *
     * @param config 对应商户-app 级别配置类
     * @param businessKey 业务唯一key  考虑是否需要，是否可以通过config 获取到
     * @param executeParams 执行入参
     * @param businessExecuteFunctionList 接入的多个组件 比如短信 a、b、c 对应的执行 Function 集合
     */
    public <I, R> R execute(BusinessFailBackConfig config, String businessKey, I executeParams, Map<String, Function<I, R>> businessExecuteFunctionList) {
        if (config == null || CollectionUtils.isEmpty(config.getChannels())) {
            return null;
        }
        R r = null;
        boolean executeResult = false;
        // todo 是否考虑要加锁
        // 配置 排序等,暂时空实现
        globalPreExecute(config, businessKey);
        List<BusinessFailBackConfig.Channel> channels = config.getChannels();
        for (int i = 0, size = channels.size(); i < size; i++) {
            BusinessFailBackConfig.Channel channel = channels.get(i);
            // 根据 config 判断 是否可以执行，此处子类根据config 获取熔断逻辑,例如 redis 、local、es 做的滑动窗口统计
            // true 可以执行，  false 降级
            boolean b = preExecute(config, channel);
            if (!b) {
                log.info("channel FailBack ,execute next:{}", channel.getChannelCode());
                continue;
            }
            try {
                // 根据 config 配置顺序, 执行
                // todo 是否需要怼结果 r 判断
                r = businessExecuteFunctionList.get(channel.getChannelCode()).apply(executeParams);
                executeResult = true;
                break;
            } catch (Exception e) {
                log.info("execute fail config:{} businessKey:{}", config, businessKey, e);
            } finally {
                // 根据config,是否熔断 和执行结果 记录一些操作，根据执行结果记录 滑动窗口数据
                afterExecute(config, channel, !b, executeResult);
            }
        }
        globalAfterExecute(config, businessKey);
        return r;
    }

    /**
     * execute 全局 前置 方法， 例如根据业务id 加锁等操作
     * @param config
     */
    void globalPreExecute(BusinessFailBackConfig config, String businessKey) {

    }

    /**
     * execute 全局 后置方法， 例如根据业务id 加锁等操作
     * @param config
     */
    void globalAfterExecute(BusinessFailBackConfig config, String businessKey) {

    }

    /**
     * execute 循环内 前置 方法， 例如判断是否熔断等
     * @param config
     * @param currentChannel
     * @return  true是可以执行 ，false 是不执行
     */
    abstract boolean preExecute(BusinessFailBackConfig config, BusinessFailBackConfig.Channel currentChannel);

    /**
     * execute 循环内 操作，记录结果到 滑动窗口中等
     * @param config
     * @param currentChannel
     * @param failBack
     * @param executeResult
     * @return
     */
    abstract boolean afterExecute(BusinessFailBackConfig config, BusinessFailBackConfig.Channel currentChannel, boolean failBack, boolean executeResult);

    static String getStatisticsKey(BusinessFailBackConfig config, BusinessFailBackConfig.Channel currentChannel) {
        return Joiner.on(":").join(Lists.newArrayList(config.getTenantId(), config.getAppCode(), currentChannel.getChannelCode()));
    }

}
