package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.entity.OperateLog;
import org.springframework.stereotype.Service;

/**
 * 账号操作记录(OperateLog) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:07:58
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OperateLogService extends CrudService<OperateLog, java.lang.Long> {

}