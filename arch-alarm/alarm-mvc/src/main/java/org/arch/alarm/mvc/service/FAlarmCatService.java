package org.arch.alarm.mvc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.AlarmCatService;
import org.arch.alarm.api.dto.AlarmCatDto;
import org.arch.alarm.mvc.dao.AlarmCatDao;
import org.arch.alarm.mvc.entity.AlarmCatEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 履约预警分类 预警树结构名称空间，对预警的分类，预警的级别，预警的名称等的定义 服务类接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Service("alarmCatService")
@Slf4j
@RequiredArgsConstructor
public class FAlarmCatService implements AlarmCatService {

    private final AlarmCatDao alarmCatDao;

    @Override
    public Long saveCat(AlarmCatDto alarmCatDto) {
        AlarmCatEntity alarmCatEntity = convert(alarmCatDto, AlarmCatEntity.class);
        if (alarmCatDao.saveOrUpdate(alarmCatEntity)) {
            return alarmCatEntity.getId();
        }
        return 0L;
    }

    public AlarmCatDto getAlarmCatById(Long id) {
        AlarmCatEntity catEntity = alarmCatDao.getById(id);
        return convert(catEntity, AlarmCatDto.class);
    }

    @Override
    public List<AlarmCatDto> listAlarmCat(AlarmCatDto alarmCatDto) {
        AlarmCatEntity alarmCatEntity = convert(alarmCatDto, AlarmCatEntity.class);
        List<AlarmCatEntity> alarmCatEntities = alarmCatDao.list(new QueryWrapper<>(alarmCatEntity));
        List<AlarmCatDto> alarmCatDtos = converts(alarmCatEntities, AlarmCatDto.class);
        return alarmCatDtos;
    }


}
