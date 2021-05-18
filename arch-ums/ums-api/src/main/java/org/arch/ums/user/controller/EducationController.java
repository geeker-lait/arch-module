package org.arch.ums.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.EducationRequest;
import org.arch.ums.user.dto.EducationSearchDto;
import org.arch.ums.user.entity.Education;
import org.arch.ums.user.service.EducationService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 用户学历信息(Education) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-03-01 00:21:11
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/education")
public class EducationController implements CrudController<EducationRequest, Education, Long,
        EducationSearchDto, EducationService> {

    private final TenantContextHolder tenantContextHolder;
    private final EducationService educationService;

    @Override
    public Education resolver(TokenInfo token, EducationRequest request) {
        Education education = new Education();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, education);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            education.setTenantId(token.getTenantId());
        }
        else {
            education.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return education;
    }

    @Override
    public EducationService getCrudService() {
        return educationService;
    }

    @Override
    public EducationSearchDto getSearchDto() {
        return new EducationSearchDto();
    }

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<EducationSearchDto> findOne(@RequestBody EducationRequest request, TokenInfo token) {
        try {
            Education education = resolver(token, request);
            EducationSearchDto searchDto = convertSearchDto(education);
            Education result = getCrudService().findOneByMapParams(searchDto.searchParams());
            return Response.success(convertSearchDto(result));
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
     * @param request     实体的 request 类型
     * @param token       token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<EducationSearchDto>> find(@RequestBody EducationRequest request, TokenInfo token) {
        Education education = resolver(token, request);
        EducationSearchDto searchDto = convertSearchDto(education);
        try {
            List<Education> educationList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(educationList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<EducationSearchDto>> page(@RequestBody EducationRequest request,
                                                    @PathVariable(value = "pageNumber") Integer pageNumber,
                                                    @PathVariable(value = "pageSize") Integer pageSize,
                                                    TokenInfo token) {
        Education education = resolver(token, request);
        EducationSearchDto searchDto = convertSearchDto(education);
        try {
            IPage<Education> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
