package org.arch.framework.id.impl;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.arch.framework.api.IdKey;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.commands.JedisCommands;
import redis.clients.jedis.params.SetParams;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class SnowflakeIdGeneratorForJedis {

    private static final long TWEPOCH = 1483200000000L;
    private static final long WORKER_ID_BITS = 10L;
    private static final long MAX_WORKER_ID = 1023L;
    private static final long SEQUENCE_BITS = 12L;
    private static final long WORKER_ID_SHIFT = 12L;
    private static final long TIMESTAMP_LEFT_SHIFT = 22L;
    private static final long SEQUENCE_MASK = 4095L;
    private Integer workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    protected RedisConnectionFactory redisConnectionFactory;
    protected RedisTemplate redisTemplate;

    public SnowflakeIdGeneratorForJedis(RedisConnectionFactory redisConnectionFactory, RedisTemplate redisTemplate) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisTemplate = redisTemplate;
    }

    private void init(IdKey idType) {
//        RedisConnection connection = this.jedisConnectionFactory.getConnection();
        RedisConnection connection = this.redisConnectionFactory.getConnection();
        JedisCommands jedis = (JedisCommands)connection.getNativeConnection();

        try {
            String workerIdRedisKey = idType.getKey();
            boolean hasGetWorkerId = false;

            for(int i = 1; (long)i <= 1023L; ++i) {
                String workerIdKey = workerIdRedisKey + i;
                String value = jedis.get(workerIdKey);
                if (value == null) {
                    // 1分44秒
                    value = jedis.set(workerIdKey, String.valueOf(System.currentTimeMillis()),SetParams.setParams().nx().px(86400L));
                    if (value != null) {
                        this.workerId = i;
                        hasGetWorkerId = true;
                        break;
                    }
                }
            }

            if (!hasGetWorkerId) {
                throw new BusinessException(CommonStatusCode.WORK_ID_GENERATE_FAILED);
            }

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(() -> {
//                RedisConnection connection1 = this.jedisConnectionFactory.getConnection();
                RedisConnection connection1 = this.redisConnectionFactory.getConnection();
                JedisCommands jedis1 = (JedisCommands)connection.getNativeConnection();

                try {
                    String workerIdKey = workerIdRedisKey + this.workerId;
                    log.info("SnowflakeIdGenerator机器号预占续期，{}", workerIdKey);
                    jedis1.expire(workerIdKey, 86400);
                } finally {
                    connection1.close();
                }

            }, 3600L, 3600L, TimeUnit.SECONDS);
        } finally {
            connection.close();
        }

    }

    public synchronized long nextId(IdKey idType) {
        init(idType);

        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1L & 4095L;
            if (this.sequence == 0L) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0L;
        }

        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

        this.lastTimestamp = timestamp;
        long nextId = timestamp - 1483200000000L << 22 | (long)(this.workerId << 12) | this.sequence;
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp;
        for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
        }

        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
