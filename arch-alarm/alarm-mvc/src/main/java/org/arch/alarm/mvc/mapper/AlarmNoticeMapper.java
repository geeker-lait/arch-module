package org.arch.alarm.mvc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.arch.alarm.mvc.entity.AlarmNoticeEntity;

/**
 * 履约告警规则维度 确定哪些仓店使用那些通知规则，并确定接受预警通知的用户配置 Mapper 接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
public interface AlarmNoticeMapper extends BaseMapper<AlarmNoticeEntity> {

}
