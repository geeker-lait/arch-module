package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dto.OperateLogSearchDto;
import org.arch.ums.account.entity.OperateLog;
import org.arch.ums.account.service.OperateLogService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.beans.Response;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 账号操作记录(OperateLog) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-03-01 00:22:29
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/operate/log")
public class OperateLogController implements CrudController<OperateLog, java.lang.Long, OperateLogSearchDto, OperateLogService> {

    private final TenantContextHolder tenantContextHolder;
    private final OperateLogService operateLogService;

    @Override
    public OperateLog resolver(TokenInfo token, OperateLog operateLog) {
        if (isNull(operateLog)) {
            operateLog = new OperateLog();
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
     * @param entity 实体类
     * @param token  token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<OperateLog> findOne(@RequestBody OperateLog entity, TokenInfo token) {
        try {
            resolver(token, entity);
            OperateLogSearchDto searchDto = convertSearchDto(entity);
            OperateLog t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(t);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(), "查询到多个结果");
            }
            else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param t     实体类
     * @param token token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<OperateLog>> find(@RequestBody OperateLog t, TokenInfo token) {
        resolver(token, t);
        OperateLogSearchDto searchDto = convertSearchDto(t);
        try {
            return Response.success(getCrudService().findAllByMapParams(searchDto.getSearchParams()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param entity     实体类
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<OperateLog>> page(@RequestBody OperateLog entity,
                                            @PathVariable(value = "pageNumber") Integer pageNumber,
                                            @PathVariable(value = "pageSize") Integer pageSize,
                                            TokenInfo token) {
        resolver(token, entity);
        OperateLogSearchDto searchDto = convertSearchDto(entity);
        try {
            return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
