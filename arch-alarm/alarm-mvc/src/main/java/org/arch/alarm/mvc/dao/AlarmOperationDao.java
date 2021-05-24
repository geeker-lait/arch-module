package org.arch.alarm.mvc.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.mvc.entity.AlarmOperationEntity;
import org.arch.alarm.mvc.mapper.AlarmOperationMapper;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 履约预警作业维度 基于作业维度，围绕着仓店和订单，便于知道和定位那个仓店的， 哪个订单，哪个作业人员，做如何的操作等， 这里支持一个订单可以有多个人去作业 服务实现类
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Slf4j
@Repository
public class AlarmOperationDao extends ServiceImpl<AlarmOperationMapper, AlarmOperationEntity> implements CrudDao<AlarmOperationEntity> {

}
