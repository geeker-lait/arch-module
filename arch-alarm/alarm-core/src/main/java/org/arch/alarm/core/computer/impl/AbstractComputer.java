package org.arch.alarm.core.computer.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.core.computer.ComputerRegistable;
import org.arch.alarm.mvc.entity.AlarmComputerEntity;
import org.arch.alarm.mvc.entity.AlarmParamsEntity;
import org.arch.alarm.mvc.mapper.AlarmComputerMapper;
import org.arch.alarm.mvc.mapper.AlarmParamsMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Slf4j
public abstract class AbstractComputer /*extends GenericValueCacheManager<JSONObject, String>*/ implements InitializingBean, ComputerRegistable {
    @Autowired
    protected AlarmComputerMapper alarmComputerMapper;
    @Autowired
    protected AlarmParamsMapper alarmParamsMapper;
//    @Autowired
//    protected IAlarmRegService iAlarmRegService;
    protected final static Map<String, List<AlarmParamsEntity>> REG_PARAM_MAP = new HashMap<>();

    @Value("${spring.alarm.initOsfAlarmCenterDb:false}")
    private Boolean initOsfAlarmCenterDb;

    /**
     * 自动初始化到数据库
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (initOsfAlarmCenterDb) {
            AlarmComputerEntity alarmComputerEntity = buildAlarmComputer();
            AlarmComputerEntity result = alarmComputerMapper.selectOne(new QueryWrapper<>(alarmComputerEntity));
            if (null == result) {
                alarmComputerMapper.insert(alarmComputerEntity);
                result = alarmComputerEntity;
            } else {
                log.warn("数据库中已存在配置 {}", alarmComputerEntity);
            }
            // 初始化计算参数
            //List<AlarmParamsEntity> alarmParamEntityList = alarmParamsMapper.selectList(new QueryWrapper<>(AlarmParamsEntity.builder().computerId(result.getId()).build()));
            //REG_PARAM_MAP.put(this.getRegKey(), alarmParamEntityList);
        }
    }

    /**
     * 刷新规则，当后台后改动时通知这里刷新
     *
     * @param computerId
     */
    @Override
    public void refreshRegParam(Long computerId) {
        //List<AlarmParamsEntity> alarmParamEntityList = alarmParamsMapper.selectList(new QueryWrapper<>(AlarmParamsEntity.builder().computerId(computerId).build()));
        //REG_PARAM_MAP.put(this.getRegKey(), alarmParamEntityList);
    }


    public abstract AlarmComputerEntity buildAlarmComputer();

    public void update(String key, JSONObject object, Long cacheExpiry) {
//        RedisTemplate<String, JSONObject> redisTemplate = this.getRedisTemplate();
//        redisTemplate.opsForValue().set(key, object, cacheExpiry, TimeUnit.MINUTES);
    }

    public JSONObject get(String key) {
        JSONObject object = null;
//        RedisTemplate<String, JSONObject> redisTemplate = this.getRedisTemplate();
//        if (redisTemplate != null) {
//            object = redisTemplate.opsForValue().get(key);
//        }
        return object;
    }

    public void remove(String key) {
//        RedisTemplate<String, JSONObject> redisTemplate = this.getRedisTemplate();
//        if (redisTemplate != null && redisTemplate.hasKey(key)) {
//            redisTemplate.delete(key);
//        }
    }

    @Data
    protected class OrderId {
        private String orderId;
    }
}
