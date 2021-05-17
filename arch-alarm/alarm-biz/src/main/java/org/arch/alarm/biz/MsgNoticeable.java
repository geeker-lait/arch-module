package org.arch.alarm.biz;

import org.arch.alarm.api.dto.AlarmChannelDto;
import org.arch.alarm.api.dto.AlarmNoticeDto;
import org.arch.alarm.api.dto.AlarmTemplateDto;
import org.arch.alarm.api.enums.MsgNotifier;
import org.arch.alarm.api.pojo.AlarmMsgData;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/8/2021 6:24 PM
 */
public interface MsgNoticeable {
    MsgNotifier getNotifier();

    void notice(AlarmTemplateDto alarmTemplateDto, AlarmChannelDto alarmChannelDto, AlarmNoticeDto alarmNoticeDto, AlarmMsgData alarmMsgData);
}
