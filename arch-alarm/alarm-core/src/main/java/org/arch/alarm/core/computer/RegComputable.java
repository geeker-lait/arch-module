package org.arch.alarm.core.computer;

import org.arch.alarm.api.dto.AlarmParamsDto;
import org.arch.alarm.api.pojo.biz.AlarmRegData;
import org.arch.alarm.api.pojo.ComputResult;
import org.arch.alarm.mvc.entity.AlarmRegEntity;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/29/2021 12:58 AM
 */
public interface RegComputable {

    /**
     * @param alarmRegEntity 规则参数实体
     * @param alarmParams    规则参数
     * @param alarmRegDatas     规则参数值
     * @param computResult   计算结果
     */
    void compute(AlarmRegEntity alarmRegEntity, List<AlarmParamsDto> alarmParams, List<? extends AlarmRegData> alarmRegDatas, ComputResult computResult);
}
