package org.arch.alarm.mvc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.AlarmNoticeService;
import org.arch.alarm.api.dto.AlarmNoticeDto;
import org.arch.alarm.mvc.dao.AlarmNoticeDao;
import org.arch.alarm.mvc.entity.AlarmNoticeEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 履约告警规则维度 确定哪些仓店使用那些通知规则，并确定接受预警通知的用户配置 服务类接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
//@Service(interfaceClass = AlarmNoticeService.class, timeout = 600000, interfaceName = "alarmNoticeService",registry = "zookeeper")
@Component("alarmNoticeService")
@Slf4j
@RequiredArgsConstructor
public class FAlarmNoticeService implements AlarmNoticeService {
    private final AlarmNoticeDao alarmNoticeDao;


    @Override
    public List<AlarmNoticeDto> getAlarmNotices(Long regId) {
        List<AlarmNoticeEntity> alarmNoticeEntities = alarmNoticeDao.list(new QueryWrapper<>(AlarmNoticeEntity.builder().regId(regId).build()));
        return converts(alarmNoticeEntities, AlarmNoticeDto.class);
    }


}
