package org.arch.alarm.api;


import org.arch.alarm.api.dto.AlarmDicDto;

import java.util.List;

/**
 * 履约预警规则字典纬度 预警规则字典 服务类接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:53 PM
 */
public interface AlarmDicService extends Convertable {

    List<Long> configDic(AlarmDicDto... alarmDicDtos);

}
