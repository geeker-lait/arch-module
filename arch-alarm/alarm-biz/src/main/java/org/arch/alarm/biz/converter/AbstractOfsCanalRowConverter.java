package org.arch.alarm.biz.converter;

import org.arch.alarm.api.pojo.biz.AlarmRegData;
import org.arch.alarm.biz.CanalRowConvertable;
import org.arch.alarm.biz.config.AlarmConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/28/2021 12:29 PM
 */
public abstract class AbstractOfsCanalRowConverter<T extends AlarmRegData> implements CanalRowConvertable<T> {

    @Autowired
    protected AlarmConfig alarmConfig;


}
