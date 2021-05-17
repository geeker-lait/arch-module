package org.arch.alarm.mvc.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.mvc.entity.AlarmStoreEntity;
import org.arch.alarm.mvc.mapper.AlarmStoreMapper;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 履约告警仓店实体 通过仓店配置，将storeNo 和catId建立关联关系，开启对仓店的监控 同时可选择开启监控那些指标regJson 服务实现类
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Slf4j
@Repository
public class AlarmStoreDao extends ServiceImpl<AlarmStoreMapper, AlarmStoreEntity> implements CrudDao<AlarmStoreEntity> {

}
