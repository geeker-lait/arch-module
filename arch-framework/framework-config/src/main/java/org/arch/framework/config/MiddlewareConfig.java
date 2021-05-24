package org.arch.framework.config;

import lombok.Builder;
import lombok.Data;
import org.arch.framework.config.model.*;

import java.util.List;

@Data
@Builder
public class MiddlewareConfig {
    private List<JedisConfigModel> jedisConfigModels;
    private List<HbaseConfigModel> hbaseConfigModels;
    private List<DruidConfigModel> druidConfigModels;
    private List<EsHighRestConfigModel> esHighRestConfigModels;
    private List<RocketmqProducerConfigModel> rocketmqProducerConfigModels;
    private List<RocketmqConsumerConfigModel> rocketmqConsumerConfigModels;
}
