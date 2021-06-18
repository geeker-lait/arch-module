package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.user.dto.JobRequest;
import org.arch.ums.user.dto.JobSearchDto;
import org.arch.ums.user.entity.Job;
import org.arch.ums.user.service.JobService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.beans.Response;
import org.springframework.beans.BeanUtils;
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
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 用户工作信息(Job) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 23:08:42
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/job")
public class JobController implements CrudController<JobRequest, Job, java.lang.Long, JobSearchDto, JobService> {

    private final TenantContextHolder tenantContextHolder;
    private final JobService jobService;

    @Override
    public Job resolver(TokenInfo token, JobRequest request) {
        Job job = new Job();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, job);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            job.setTenantId(token.getTenantId());
        }
        else {
            job.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return job;
    }

    @Override
    public JobService getCrudService() {
        return jobService;
    }

    @Override
    public JobSearchDto getSearchDto() {
        return new JobSearchDto();
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
    public Response<JobSearchDto> findOne(@RequestBody @Valid JobRequest request, TokenInfo token) {
        try {
            Job job = resolver(token, request);
            JobSearchDto searchDto = convertSearchDto(job);
            Job result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<JobSearchDto>> find(@RequestBody @Valid JobRequest request, TokenInfo token) {
        Job job = resolver(token, request);
        JobSearchDto searchDto = convertSearchDto(job);
        try {
            List<Job> jobList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(jobList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<JobSearchDto>> page(@RequestBody @Valid JobRequest request,
                                              @PathVariable(value = "pageNumber") Integer pageNumber,
                                              @PathVariable(value = "pageSize") Integer pageSize,
                                              TokenInfo token) {
        Job job = resolver(token, request);
        JobSearchDto searchDto = convertSearchDto(job);
        try {
            IPage<Job> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
