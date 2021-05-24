package org.arch.alarm.mvc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.arch.alarm.mvc.entity.AlarmOperationEntity;

/**
 * 履约预警作业维度 基于作业维度，围绕着仓店和订单，便于知道和定位那个仓店的， 哪个订单，哪个作业人员，做如何的操作等， 这里支持一个订单可以有多个人去作业 Mapper 接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
public interface AlarmOperationMapper extends BaseMapper<AlarmOperationEntity> {

}
