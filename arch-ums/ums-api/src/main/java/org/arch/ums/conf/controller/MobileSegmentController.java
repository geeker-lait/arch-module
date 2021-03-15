package org.arch.ums.conf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.conf.dto.MobileSegmentSearchDto;
import org.arch.ums.conf.entity.MobileSegment;
import org.arch.ums.conf.service.MobileSegmentService;
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
 * 手机号段信息(MobileSegment) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:48:56
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/conf/mobile/segment")
public class MobileSegmentController implements CrudController<MobileSegment, java.lang.Long, MobileSegmentSearchDto, MobileSegmentService> {

    private final TenantContextHolder tenantContextHolder;
    private final MobileSegmentService mobileSegmentService;

    @Override
    public MobileSegment resolver(TokenInfo token, MobileSegment mobileSegment) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 mobileSegment 后返回 mobileSegment, 如: tenantId 的处理等.
        if (isNull(mobileSegment)) {
            mobileSegment = new MobileSegment();
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            mobileSegment.setTenantId(token.getTenantId());
        }
        else {
            mobileSegment.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return mobileSegment;
    }

    @Override
    public MobileSegmentService getCrudService() {
        return mobileSegmentService;
    }

    @Override
    public MobileSegmentSearchDto getSearchDto() {
        return new MobileSegmentSearchDto();
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
    public Response<MobileSegment> findOne(@RequestBody MobileSegment entity, TokenInfo token) {
        try {
            resolver(token, entity);
            MobileSegmentSearchDto searchDto = convertSearchDto(entity);
            MobileSegment t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
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
    public Response<List<MobileSegment>> find(@RequestBody MobileSegment t, TokenInfo token) {
        resolver(token, t);
        MobileSegmentSearchDto searchDto = convertSearchDto(t);
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
    public Response<IPage<MobileSegment>> page(@RequestBody MobileSegment entity,
                                               @PathVariable(value = "pageNumber") Integer pageNumber,
                                               @PathVariable(value = "pageSize") Integer pageSize,
                                               TokenInfo token) {
        resolver(token, entity);
        MobileSegmentSearchDto searchDto = convertSearchDto(entity);
        try {
            return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
