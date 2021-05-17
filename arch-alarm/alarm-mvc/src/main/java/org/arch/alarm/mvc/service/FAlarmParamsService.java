package org.arch.alarm.mvc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.arch.alarm.api.AlarmParamsService;
import org.arch.alarm.api.dto.AlarmParamsDto;
import org.arch.alarm.mvc.dao.AlarmParamsDao;
import org.arch.alarm.mvc.entity.AlarmParamsEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/28/2021 7:39 PM
 */
@RequiredArgsConstructor
@Service("alarmParamsService")
public class FAlarmParamsService implements AlarmParamsService {
    private final AlarmParamsDao alarmParamsDao;

    @Override
    public List<AlarmParamsDto> getComputerParams(String dbtb) {
        List<AlarmParamsEntity> alarmParamsEntities = alarmParamsDao.list(new QueryWrapper<>(AlarmParamsEntity.builder().dbtb(dbtb).build()));
        List<AlarmParamsDto> alarmParamsDtos = new ArrayList<>();
        alarmParamsEntities.forEach(a -> {
            AlarmParamsDto alarmParamsDto = new AlarmParamsDto();
            BeanUtils.copyProperties(a, alarmParamsDto);
            alarmParamsDtos.add(alarmParamsDto);
        });
        return alarmParamsDtos;
    }

    @Override
    public Long configParamByRegAndDic(AlarmParamsDto alarmParamsDto) {
        AlarmParamsEntity alarmParamsEntity = convert(alarmParamsDto, AlarmParamsEntity.class);
        if (alarmParamsDao.saveOrUpdate(alarmParamsEntity)) {
            return alarmParamsEntity.getId();
        } else {
            return 0L;
        }
    }
}
