package org.arch.alarm.mvc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.AlarmChannelService;
import org.arch.alarm.api.dto.AlarmChannelDto;
import org.arch.alarm.mvc.dao.AlarmChannelDao;
import org.arch.alarm.mvc.entity.AlarmChannelEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 履约预警通道维度 信息爆炸，每人每天平均要收到2条以上垃圾短信，一些重要消息很可能被遗漏，或不被引起关注。因此我们需要更人性化的让用户可以选择以何种方式发送提醒与预警，竟而更精准有效的通知到他们，如邮件、系统消息、短信、微信等。 服务类接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Component("alarmChannelService")
@Slf4j
@RequiredArgsConstructor
public class FAlarmChannelService implements AlarmChannelService {
    private final AlarmChannelDao alarmChannelDao;

    @Override
    public AlarmChannelDto getChannelById(Serializable id) {
        return convert(alarmChannelDao.getById(id), AlarmChannelDto.class);
    }

    @Override
    public List<Long> configChannel(AlarmChannelDto... channelDto) {
        final List<Long> lst = new ArrayList<>();
        Arrays.asList(channelDto).forEach(ad -> {
            AlarmChannelEntity alarmChannelEntity = new AlarmChannelEntity();
            BeanUtils.copyProperties(ad, alarmChannelEntity);
            if (null != ad.getId()) {
                alarmChannelDao.updateById(alarmChannelEntity);
            }
            if (null == alarmChannelDao.getOne(new QueryWrapper<>(alarmChannelEntity))) {
                alarmChannelDao.save(alarmChannelEntity);
            }
            lst.add(alarmChannelEntity.getId());
        });
        return lst;
    }

    @Override
    public List<AlarmChannelDto> listChannel() {
        List<AlarmChannelEntity> alarmChannelEntities = alarmChannelDao.list();
        if (null != alarmChannelEntities && alarmChannelEntities.size() > 0) {
            return converts(alarmChannelEntities, AlarmChannelDto.class);
        }
        return new ArrayList<>();
    }

}
