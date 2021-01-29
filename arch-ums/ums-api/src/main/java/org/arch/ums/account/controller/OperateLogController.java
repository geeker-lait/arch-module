package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.OperateLogSearchDto;
import org.arch.ums.account.entity.OperateLog;
import org.arch.ums.account.service.OperateLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号操作记录(OperateLog) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:07:58
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/operate/log")
public class OperateLogController implements CrudController<OperateLog, java.lang.Long, OperateLogSearchDto, OperateLogService> {

    private final OperateLogService operateLogService;

    @Override
    public OperateLog resolver(TokenInfo token, OperateLog operateLog) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 operateLog 后返回 operateLog, 如: tenantId 的处理等.
        return operateLog;
    }

    @Override
    public OperateLogService getCrudService() {
        return operateLogService;
    }

    @Override
    public OperateLogSearchDto getSearchDto() {
        return new OperateLogSearchDto();
    }

}