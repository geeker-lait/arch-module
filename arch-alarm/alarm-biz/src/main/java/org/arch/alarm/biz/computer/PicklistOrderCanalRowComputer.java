package org.arch.alarm.biz.computer;

import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.dto.AlarmParamsDto;
import org.arch.alarm.api.dto.AlarmRegDto;
import org.arch.alarm.api.enums.ComputTable;
import org.arch.alarm.api.pojo.biz.PicklistOrderRegData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: 特殊的处理
 * @weixin PN15855012581
 * @date 4/27/2021 10:33 PM
 */
@Component
@Slf4j
public class PicklistOrderCanalRowComputer extends AbstractCanalRowComputer<PicklistOrderRegData> {

    @Override
    public ComputTable getComputTable() {
        return ComputTable.PICKLIST_DB_T_TRADE_PICKLIST_ORDER;
    }

    @Override
    public void comput(AlarmRegDto alarmRegDto, List<AlarmParamsDto> alarmParamsDtos, List<PicklistOrderRegData> alarmDatas) {
        // todo 远程计算
        super.comput(alarmRegDto, alarmParamsDtos, alarmDatas);
        // todo 根据计算结果作业
    }


}
