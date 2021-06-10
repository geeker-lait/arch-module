package org.arch.ums.account.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.OperateLogRequest;
import org.arch.ums.account.dto.OperateLogSearchDto;
import org.arch.ums.account.entity.OperateLog;
import org.arch.ums.account.rest.OperateLogRest;
import org.arch.ums.account.service.OperateLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号操作记录(OperateLog) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:03
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OperateLogBiz implements CrudBiz<OperateLogRequest, OperateLog, java.lang.Long, OperateLogSearchDto, OperateLogSearchDto, OperateLogService>, OperateLogRest {

    private final TenantContextHolder tenantContextHolder;
    private final OperateLogService operateLogService;

    @Override
    public OperateLog resolver(TokenInfo token, OperateLogRequest request) {
        OperateLog operateLog = new OperateLog();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, operateLog);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            operateLog.setTenantId(token.getTenantId());
        }
        else {
            operateLog.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
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
