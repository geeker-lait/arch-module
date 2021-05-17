package org.arch.alarm.mvc.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.mvc.entity.AlarmCatEntity;
import org.arch.alarm.mvc.mapper.AlarmCatMapper;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 履约预警分类 预警树结构名称空间，对预警的分类，预警的级别，预警的名称等的定义 服务实现类
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Slf4j
@Repository
public class AlarmCatDao extends ServiceImpl<AlarmCatMapper, AlarmCatEntity> implements CrudDao<AlarmCatEntity> {

}
