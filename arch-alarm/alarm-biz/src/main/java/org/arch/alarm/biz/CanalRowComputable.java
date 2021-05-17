package org.arch.alarm.biz;

import org.arch.alarm.api.dto.AlarmParamsDto;
import org.arch.alarm.api.dto.AlarmRegDto;
import org.arch.alarm.api.enums.ComputTable;
import org.arch.alarm.api.pojo.biz.AlarmRegData;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/27/2021 10:35 PM
 */
public interface CanalRowComputable<T extends AlarmRegData> {
    ComputTable getComputTable();

    /**
     * 计算binlog数据
     *
     * @param alarmRegDto     预警规则对象
     * @param alarmParamsDtos 预警规则参数集合
     * @param alarmDatas      预警参数对应的值
     */
    void comput(AlarmRegDto alarmRegDto, List<AlarmParamsDto> alarmParamsDtos, List<T> alarmDatas);

}
