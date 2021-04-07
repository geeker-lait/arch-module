package org.arch.framework.failback;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-04-07
 */
@Data
@Accessors(chain = true)
public class BusinessFailBackConfig {

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 租户对应的app(租户与app一对多) 与租户对应
     */
    private String appCode;

    /**
     * 通道配置数据 有序的
     */
    private List<Channel> channels;

    @Data
    @Accessors(chain = true)
    public static class Channel {
        /**
         * 通道Code
         */
        private String channelCode;

        /**
         * 关系 and 和 or
         */
        private RelationEnum relation;

        /**
         * 熔断
         */
        private List<Rule> rules;

    }
    @Data
    @Accessors(chain = true)
    public static class Rule {

        private String ruleCode;


        /**
         * 失败次数 与 duration 一块使用
         */
        private Integer failNum;

        /**
         * 持续时长 - 滑动窗口时间
         */
        private Integer duration;
    }

}
