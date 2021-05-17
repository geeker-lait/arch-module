package org.arch.alarm.api;

import org.arch.alarm.api.dto.AlarmNoticeDto;

import java.util.List;

/**
 * 履约告警规则维度 确定哪些仓店使用那些通知规则，并确定接受预警通知的用户配置 服务类接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
public interface AlarmNoticeService  extends Convertable{

    /**
     * 根据规则id 查询所有需要通知对象
     * @param regId
     * @return
     */
    List<AlarmNoticeDto> getAlarmNotices(Long regId);
}
