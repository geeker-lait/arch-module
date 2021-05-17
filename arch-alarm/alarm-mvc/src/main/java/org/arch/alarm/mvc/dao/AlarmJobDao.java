package org.arch.alarm.mvc.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.mvc.entity.AlarmJobEntity;
import org.arch.alarm.mvc.mapper.AlarmJobMapper;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 履约预警job维度， 定义执行job产生相应维度的数据， 服务实现类
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Slf4j
@Repository
public class AlarmJobDao extends ServiceImpl<AlarmJobMapper, AlarmJobEntity> implements CrudDao<AlarmJobEntity> {

}
