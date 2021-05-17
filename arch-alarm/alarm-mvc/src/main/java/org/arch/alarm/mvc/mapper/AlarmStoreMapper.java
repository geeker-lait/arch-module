package org.arch.alarm.mvc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.arch.alarm.mvc.entity.AlarmStoreEntity;

/**
 * 履约告警仓店实体 通过仓店配置，将storeNo 和catId建立关联关系，开启对仓店的监控 同时可选择开启监控那些指标regJson Mapper 接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
public interface AlarmStoreMapper extends BaseMapper<AlarmStoreEntity> {

}
