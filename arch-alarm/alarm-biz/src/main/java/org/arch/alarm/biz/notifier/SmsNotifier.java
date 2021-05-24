package org.arch.alarm.biz.notifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.dto.AlarmChannelDto;
import org.arch.alarm.api.dto.AlarmNoticeDto;
import org.arch.alarm.api.dto.AlarmTemplateDto;
import org.arch.alarm.api.enums.MsgNotifier;
import org.arch.alarm.api.pojo.AlarmMsgData;
import org.arch.alarm.biz.MsgNoticeable;
import org.springframework.stereotype.Component;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/8/2021 6:30 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SmsNotifier extends AbstractMsgNotifier implements MsgNoticeable {
    @Override
    public MsgNotifier getNotifier() {
        return MsgNotifier.SMS;
    }

    @Override
    public void notice(AlarmTemplateDto alarmTemplateDto, AlarmChannelDto alarmChannelDto, AlarmNoticeDto and, AlarmMsgData alarmMsgData) {
        log.info("短信通知.....");
    }
}
