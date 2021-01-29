package org.arch.ums.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.account.entity.OperateLog;

/**
 * 账号操作记录(OperateLog) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:59:08
 * @since 1.0.0
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {

}