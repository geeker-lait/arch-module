package org.arch.ums.user.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.user.dto.JobRequest;
import org.arch.ums.user.dto.JobSearchDto;
import org.arch.ums.user.entity.Job;
import org.arch.ums.user.service.JobService;
import org.arch.ums.user.rest.JobRest;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 用户工作信息(Job) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:53:16
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class JobBiz implements CrudBiz<JobRequest, Job, java.lang.Long, JobSearchDto, JobSearchDto, JobService>, JobRest {

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
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public JobSearchDto findOne(JobRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Job job = resolver(token, request);
        JobSearchDto searchDto = convertSearchDto(job);
        Job result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<JobSearchDto> find(JobRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Job job = resolver(token, request);
        JobSearchDto searchDto = convertSearchDto(job);
        List<Job> jobList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return jobList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<JobSearchDto> page(JobRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Job job = resolver(token, request);
        JobSearchDto searchDto = convertSearchDto(job);
        IPage<Job> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
