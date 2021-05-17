package org.arch.alarm.mvc.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.mvc.entity.AlarmParamsEntity;
import org.arch.alarm.mvc.mapper.AlarmParamsMapper;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 预警规则param 预警规则计算参数 服务实现类
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Slf4j
@Repository
public class AlarmParamsDao extends ServiceImpl<AlarmParamsMapper, AlarmParamsEntity> implements CrudDao<AlarmParamsEntity> {

}
