package org.arch.alarm.mvc.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.mvc.entity.AlarmOrderEntity;
import org.arch.alarm.mvc.mapper.AlarmOrderMapper;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 履约预警订单维度 基于订单维度 服务实现类
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Slf4j
@Repository
public class AlarmOrderDao extends ServiceImpl<AlarmOrderMapper, AlarmOrderEntity> implements CrudDao<AlarmOrderEntity> {

}
