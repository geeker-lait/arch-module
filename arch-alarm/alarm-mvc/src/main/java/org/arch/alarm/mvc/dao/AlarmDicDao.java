package org.arch.alarm.mvc.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.mvc.entity.AlarmDicEntity;
import org.arch.alarm.mvc.mapper.AlarmDicMapper;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 履约预警规则字典纬度 预警规则字典 服务实现类
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:53 PM
 */
@Slf4j
@Repository
public class AlarmDicDao extends ServiceImpl<AlarmDicMapper, AlarmDicEntity> implements CrudDao<AlarmDicEntity> {

}
