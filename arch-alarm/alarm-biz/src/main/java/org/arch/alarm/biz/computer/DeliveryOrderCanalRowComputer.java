package org.arch.alarm.biz.computer;

import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.dto.AlarmParamsDto;
import org.arch.alarm.api.dto.AlarmRegDto;
import org.arch.alarm.api.enums.ComputTable;
import org.arch.alarm.api.pojo.biz.DeliveryOrderRegData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/27/2021 10:33 PM
 */
@Component
@Slf4j
public class DeliveryOrderCanalRowComputer extends AbstractCanalRowComputer<DeliveryOrderRegData> {

    @Override
    public ComputTable getComputTable() {
        return ComputTable.OFS_DELIVERY_CENTER_T_DELIVERY_ORDER;
    }

    @Override
    public void comput(AlarmRegDto alarmRegDto, List<AlarmParamsDto> alarmParamsDtos, List<DeliveryOrderRegData> alarmDatas) {
        // todo 远程计算
        super.comput(alarmRegDto, alarmParamsDtos, alarmDatas);
        // todo 根据计算结果作业

    }

}
