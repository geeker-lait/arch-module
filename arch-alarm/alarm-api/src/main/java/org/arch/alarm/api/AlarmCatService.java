package org.arch.alarm.api;

import org.arch.alarm.api.dto.AlarmCatDto;

import java.util.List;

/**
 * 履约预警分类 预警树结构名称空间，对预警的分类，预警的级别，预警的名称等的定义 服务类接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
public interface AlarmCatService  extends Convertable{
    Long saveCat(AlarmCatDto alarmCatDto);
    AlarmCatDto getAlarmCatById(Long id);
    List<AlarmCatDto> listAlarmCat(AlarmCatDto alarmCatDto);
}
