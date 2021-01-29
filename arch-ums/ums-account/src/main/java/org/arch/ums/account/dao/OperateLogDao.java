package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.OperateLog;
import org.arch.ums.account.mapper.OperateLogMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号操作记录(OperateLog) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:58:24
 * @since 1.0.0
 */
@Slf4j
@Repository
public class OperateLogDao extends ServiceImpl<OperateLogMapper, OperateLog> implements CrudDao<OperateLog> {

}