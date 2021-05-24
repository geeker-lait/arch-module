package org.arch.alarm.api;

import org.arch.alarm.api.dto.AlarmParamsDto;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/28/2021 7:22 PM
 */
public interface AlarmParamsService extends Convertable {

    /**
     * 根据库表信息查询 预警参数AlarmParams
     *
     * @param dbtb
     * @return
     */
    List<AlarmParamsDto> getComputerParams(String dbtb);

    /**
     * 配置预警参数
     * @param alarmParamsDto
     * @return
     */
    Long configParamByRegAndDic(AlarmParamsDto alarmParamsDto);
}
