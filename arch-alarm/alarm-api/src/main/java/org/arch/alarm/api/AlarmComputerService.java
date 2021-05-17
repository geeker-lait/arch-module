package org.arch.alarm.api;

import org.arch.alarm.api.dto.AlarmParamsDto;
import org.arch.alarm.api.pojo.biz.AlarmRegData;
import org.arch.alarm.api.pojo.ComputResult;

import java.util.List;

/**
 * 预警规则计算bean 预警规则计算bean，spring启动时会注册 服务类接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:53 PM
 */
public interface AlarmComputerService  extends Convertable{
    /**
     * 计算规则
     *
     * @param alarmParamsDtos 计算参数
     * @param alarmDatas      规则数据
     * @return
     */
    ComputResult compute(List<AlarmParamsDto> alarmParamsDtos, List<? extends AlarmRegData> alarmDatas);
}
