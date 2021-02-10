package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.JobSearchDto;
import org.arch.ums.user.entity.Job;
import org.arch.ums.user.service.JobService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 用户工作信息(Job) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:31:17
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/job")
public class JobController implements CrudController<Job, Long, JobSearchDto, JobService> {

    private final TenantContextHolder tenantContextHolder;
    private final JobService jobService;

    @Override
    public Job resolver(TokenInfo token, Job job) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 job 后返回 job, 如: tenantId 的处理等.
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

}