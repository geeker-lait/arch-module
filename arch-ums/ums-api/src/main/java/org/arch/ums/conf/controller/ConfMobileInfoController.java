package org.arch.ums.conf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.conf.dto.ConfMobileInfoSearchDto;
import org.arch.ums.conf.entity.ConfMobileInfo;
import org.arch.ums.conf.service.ConfMobileInfoService;
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
 * 手机号归属地信息(ConfMobileInfo) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:48:57
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/conf/mobile/info")
public class ConfMobileInfoController implements CrudController<ConfMobileInfo, java.lang.Long, ConfMobileInfoSearchDto, ConfMobileInfoService> {

    private final TenantContextHolder tenantContextHolder;
    private final ConfMobileInfoService confMobileInfoService;

    @Override
    public ConfMobileInfo resolver(TokenInfo token, ConfMobileInfo confMobileInfo) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 confMobileInfo 后返回 confMobileInfo, 如: tenantId 的处理等.
        if (isNull(confMobileInfo)) {
            confMobileInfo = new ConfMobileInfo();
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            confMobileInfo.setTenantId(token.getTenantId());
        }
        else {
            confMobileInfo.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return confMobileInfo;
    }

    @Override
    public ConfMobileInfoService getCrudService() {
        return confMobileInfoService;
    }

    @Override
    public ConfMobileInfoSearchDto getSearchDto() {
        return new ConfMobileInfoSearchDto();
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
    public Response<ConfMobileInfo> findOne(@RequestBody ConfMobileInfo entity, TokenInfo token) {
        try {
            resolver(token, entity);
            ConfMobileInfoSearchDto searchDto = convertSearchDto(entity);
            ConfMobileInfo t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
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
    public Response<List<ConfMobileInfo>> find(@RequestBody ConfMobileInfo t, TokenInfo token) {
        resolver(token, t);
        ConfMobileInfoSearchDto searchDto = convertSearchDto(t);
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
    public Response<IPage<ConfMobileInfo>> page(@RequestBody ConfMobileInfo entity,
                                                @PathVariable(value = "pageNumber") Integer pageNumber,
                                                @PathVariable(value = "pageSize") Integer pageSize,
                                                TokenInfo token) {
        resolver(token, entity);
        ConfMobileInfoSearchDto searchDto = convertSearchDto(entity);
        try {
            return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
