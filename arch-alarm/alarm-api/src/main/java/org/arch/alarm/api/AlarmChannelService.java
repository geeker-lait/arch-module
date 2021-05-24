package org.arch.alarm.api;

import org.arch.alarm.api.dto.AlarmChannelDto;

import java.io.Serializable;
import java.util.List;

/**
 * 履约预警通道维度 信息爆炸，每人每天平均要收到2条以上垃圾短信，一些重要消息很可能被遗漏，或不被引起关注。因此我们需要更人性化的让用户可以选择以何种方式发送提醒与预警，竟而更精准有效的通知到他们，如邮件、系统消息、短信、微信等。 服务类接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
public interface AlarmChannelService extends Convertable {
    AlarmChannelDto getChannelById(Serializable id);

    List<Long> configChannel(AlarmChannelDto... channelDto);

    List<AlarmChannelDto> listChannel();
}
