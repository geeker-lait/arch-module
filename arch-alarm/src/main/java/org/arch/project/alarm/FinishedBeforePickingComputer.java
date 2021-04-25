package org.arch.project.alarm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.project.alarm.entity.AlarmComputerEntity;
import org.arch.project.alarm.entity.AlarmParamEntity;
import org.arch.project.alarm.mapper.AlarmComputerMapper;
import org.arch.project.alarm.mapper.AlarmParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: 提前抛单计算(捡获完成之前)
 * @weixin PN15855012581
 * @date :
 */
@Slf4j
@Service("finishedBeforePickingComputer")
@RequiredArgsConstructor
public class FinishedBeforePickingComputer extends AbstractRegComputer implements RegComputeable {



    @Override
    public String getRegKey() {
        return RegDescr.FINISHED_BEFORRE_PICKING.getRegKey();
    }

    @Override
    public boolean compute(String expression) {
        // todo 处理表达式
        //expressionHandler.resolve(expression);
        for (AlarmParamEntity alarmParamEntity : REG_PARAM_MAP.get(getRegKey())) {

        }
        return false;

    }




}
