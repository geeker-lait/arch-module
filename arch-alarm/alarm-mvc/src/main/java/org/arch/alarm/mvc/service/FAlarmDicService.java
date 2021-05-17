package org.arch.alarm.mvc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.AlarmDicService;
import org.arch.alarm.api.dto.AlarmDicDto;
import org.arch.alarm.mvc.dao.AlarmDicDao;
import org.arch.alarm.mvc.entity.AlarmDicEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 履约预警规则字典纬度 预警规则字典 服务类接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:53 PM
 */
//@Service(interfaceClass = AlarmDicService.class)
@Component("alarmDicService")
@Slf4j
@RequiredArgsConstructor
public class FAlarmDicService implements AlarmDicService {
    private final AlarmDicDao alarmDicDao;

    @Override
    public List<Long> configDic(AlarmDicDto... alarmDicDtos) {
        final List<Long> lst = new ArrayList<>();
        Arrays.asList(alarmDicDtos).forEach(ad -> {
            AlarmDicEntity alarmDicEntity = new AlarmDicEntity();
            BeanUtils.copyProperties(ad, alarmDicEntity);
            if (null != ad.getId()) {
                alarmDicDao.updateById(alarmDicEntity);
            }
            if (null == alarmDicDao.getOne(new QueryWrapper<>(alarmDicEntity))) {
                alarmDicDao.save(alarmDicEntity);
            }
            lst.add(alarmDicEntity.getId());
        });
        return lst;
    }
}
