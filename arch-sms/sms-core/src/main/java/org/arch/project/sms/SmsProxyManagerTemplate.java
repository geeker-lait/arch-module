package org.arch.project.sms;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.db.AbstractDb;
import com.google.common.util.concurrent.RateLimiter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public class SmsProxyManagerTemplate {
    public static final Map<Integer, RateLimiter> GLOBAL_RATE_LIMITERS = new HashMap<>();
    public static final Integer DEFAULT_LIMIT_SPEED = 10;
}
