package org.arch.ums.user.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.JobRequest;
import org.arch.ums.user.dto.JobSearchDto;
import org.arch.ums.user.entity.Job;
import org.arch.ums.user.service.JobService;
import org.arch.ums.user.rest.JobRest;
import org.springframework.beans.BeanUtils;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

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

}
