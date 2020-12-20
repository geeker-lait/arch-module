package org.arch.project.event.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/16/2020 4:49 PM
 * @description: 履约流程配置类,这里的粒度可以更细，直接到作业级别
 */
@Component
@ConfigurationProperties(prefix = "work")
@Data
public class WorkConfiguration {

    private StoreCoreWorkProperties storeCoreWorkProperties;

    private StoreInnerWorkProperties storeInnerWorkProperties;

    private StoreOuterWorkProperties storeOuterWorkProperties;

    private StoreTailWorkProperties storeTailWorkProperties;
/*    // 核心作业
    @Value("seq.store-core-work")
    private int storeCoreWork;
    // 仓内作业
    @Value("seq.store-inner-work")
    private int storeInnerWork;
    // 仓位作业
    @Value("seq.store-outer-work")
    private int storeOuterWork;
    // 末端作业
    @Value("seq.store-tailer-work")
    private int storeTailerWork;*/
}
