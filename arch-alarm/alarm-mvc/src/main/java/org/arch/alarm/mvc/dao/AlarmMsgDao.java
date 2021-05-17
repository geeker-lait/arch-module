package org.arch.alarm.mvc.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.mvc.entity.AlarmMsgEntity;
import org.arch.alarm.mvc.mapper.AlarmMsgMapper;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 履约预警消息维度， 执行预警发送的消息 服务实现类
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Slf4j
@Repository
public class AlarmMsgDao extends ServiceImpl<AlarmMsgMapper, AlarmMsgEntity> implements CrudDao<AlarmMsgEntity> {

}
