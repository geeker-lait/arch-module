package org.arch.alarm.mvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.AlarmTemplateService;
import org.arch.alarm.api.dto.AlarmTemplateDto;
import org.arch.alarm.mvc.dao.AlarmTemplateDao;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 履约预警通知模板 定义不同场景，不同的通知模式（direct，fanout，topic等），不同的消息内容，不同的通知通道。如需要针对骑手提货异常发送微信消息提醒，选择微信通知，选择通知模板，编辑或使用规则模板内容或使用自定义通知内容完成配置，当到达业务预值时，触发该通知，不仅可以同时通知到骑手，还可以通知主管，通知仓店等..... 服务类接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Component("alarmTemplateService")
@Slf4j
@RequiredArgsConstructor
public class FAlarmTemplateService implements AlarmTemplateService {
    private final AlarmTemplateDao alarmTemplateDao;

    @Override
    public AlarmTemplateDto getTemplateById(Serializable id) {
        return convert(alarmTemplateDao.getById(id), AlarmTemplateDto.class);
    }

}
