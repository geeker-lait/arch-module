package org.arch.alarm.mvc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.arch.alarm.mvc.entity.AlarmRegEntity;

/**
 * 履约预警规则维度 预警规则，如： 规则1：运单状态为委托接受，超过X时间未发货的运单，给出提醒。 规则2：运单状态为提货，未到货的运单，如果当前时间减去接单时间，超过标准时效，则给出提醒，将延误。 规则3：运单状态为发货确认或提货，当前时间减去入场时间，超过X时间未出场的，给出提醒。 规则4：运单状态提货，超过X时间，车辆/骑手的LBS地址没有变化，则给出预警提示，送货异常 Mapper 接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
public interface AlarmRegMapper extends BaseMapper<AlarmRegEntity> {

}
