package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.OperateLog;

/**
 * 账号操作记录(OperateLog) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:08:00
 * @since 1.0.0
 */
@Mapper
public interface OperateLogMapper extends CrudMapper<OperateLog> {

}