package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.OperateLogRequest;
import org.arch.ums.account.dto.OperateLogSearchDto;
import org.arch.ums.account.entity.OperateLog;
import org.arch.ums.account.rest.OperateLogRest;
import org.arch.ums.account.service.OperateLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public OperateLogSearchDto findOne(OperateLogRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        OperateLog operateLog = resolver(token, request);
        OperateLogSearchDto searchDto = convertSearchDto(operateLog);
        OperateLog result = getCrudService().findOneByMapParams(searchDto.searchParams());
        return convertReturnDto(result);
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO List
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<OperateLogSearchDto> find(OperateLogRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        OperateLog operateLog = resolver(token, request);
        OperateLogSearchDto searchDto = convertSearchDto(operateLog);
        List<OperateLog> operateLogList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return operateLogList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @return {@link IPage}
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public IPage<OperateLogSearchDto> page(OperateLogRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        OperateLog operateLog = resolver(token, request);
        OperateLogSearchDto searchDto = convertSearchDto(operateLog);
        IPage<OperateLog> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
