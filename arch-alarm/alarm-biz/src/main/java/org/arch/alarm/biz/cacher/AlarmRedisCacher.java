package org.arch.alarm.biz.cacher;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/8/2021 5:50 PM
 */
@Component
@RequiredArgsConstructor
public class AlarmRedisCacher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void update(String key, Object object, Long cacheExpiry) {
        redisTemplate.opsForValue().set(key, object, cacheExpiry, TimeUnit.MINUTES);
    }

    public <T> T get(String key) {
        Object object = null;
        if (redisTemplate != null) {
            object = redisTemplate.opsForValue().get(key);
        }
        return (T) object;
    }

    public void remove(String key) {
        if (redisTemplate != null && redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }
}
