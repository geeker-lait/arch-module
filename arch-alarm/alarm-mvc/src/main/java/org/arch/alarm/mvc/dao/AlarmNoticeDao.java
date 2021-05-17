package org.arch.alarm.mvc.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.mvc.entity.AlarmNoticeEntity;
import org.arch.alarm.mvc.mapper.AlarmNoticeMapper;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 履约告警规则维度 确定哪些仓店使用那些通知规则，并确定接受预警通知的用户配置 服务实现类
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Slf4j
@Repository
public class AlarmNoticeDao extends ServiceImpl<AlarmNoticeMapper, AlarmNoticeEntity> implements CrudDao<AlarmNoticeEntity> {

}
