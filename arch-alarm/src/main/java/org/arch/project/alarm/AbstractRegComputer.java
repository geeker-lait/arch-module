package org.arch.project.alarm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.arch.project.alarm.entity.AlarmComputerEntity;
import org.arch.project.alarm.entity.AlarmParamEntity;
import org.arch.project.alarm.mapper.AlarmComputerMapper;
import org.arch.project.alarm.mapper.AlarmParamMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Slf4j
public abstract class AbstractRegComputer implements InitializingBean ,RegComputeable{
    @Autowired
    protected AlarmComputerMapper alarmComputerMapper;
    @Autowired
    protected AlarmParamMapper alarmParamMapper;
    @Autowired
    protected ExpressionHandler expressionHandler;

    protected final static Map<String, List<AlarmParamEntity>> REG_PARAM_MAP = new HashMap<>();
    /**
     * 自动初始化到数据库
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AlarmComputerEntity alarmComputerEntity = AlarmComputerEntity.builder()
                .regKey(RegDescr.FINISHED_BEFORRE_PICKING.getRegKey())
                .regName(RegDescr.FINISHED_BEFORRE_PICKING.getRegName())
                .build();
        AlarmComputerEntity result = alarmComputerMapper.selectOne(new QueryWrapper(alarmComputerEntity));
        if (null == result) {
            alarmComputerMapper.insert(alarmComputerEntity);
            result = alarmComputerEntity;
        } else {
            log.warn("数据库中已存在配置 {}", alarmComputerEntity);
        }
        // 初始化计算参数
        List<AlarmParamEntity> alarmParamEntityList = alarmParamMapper.selectList(new QueryWrapper(AlarmParamEntity.builder().computerId(result.getId())));
        REG_PARAM_MAP.put(this.getRegKey(),alarmParamEntityList);
    }

    /**
     * 刷新规则，当后台后改动时通知这里刷新
     * @param computerId
     */
    @Override
    public void refreshRegParam(Long computerId) {
        List<AlarmParamEntity> alarmParamEntityList = alarmParamMapper.selectList(new QueryWrapper(AlarmParamEntity.builder().computerId(computerId)));
        REG_PARAM_MAP.put(this.getRegKey(),alarmParamEntityList);
    }
}
