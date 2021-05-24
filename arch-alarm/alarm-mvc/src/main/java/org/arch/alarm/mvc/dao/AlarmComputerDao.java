package org.arch.alarm.mvc.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.mvc.entity.AlarmComputerEntity;
import org.arch.alarm.mvc.mapper.AlarmComputerMapper;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 预警规则计算bean 预警规则计算bean，spring启动时会注册 服务实现类
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:53 PM
 */
@Slf4j
@Repository
public class AlarmComputerDao extends ServiceImpl<AlarmComputerMapper, AlarmComputerEntity> implements CrudDao<AlarmComputerEntity> {

}
