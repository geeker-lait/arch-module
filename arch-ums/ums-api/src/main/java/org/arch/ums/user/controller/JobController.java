package org.arch.ums.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.JobSearchDto;
import org.arch.ums.user.entity.Job;
import org.arch.ums.user.service.JobService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 用户工作信息(Job) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-26 23:19:52
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/job")
public class JobController implements CrudController<Job, java.lang.Long, JobSearchDto, JobService> {

    private final TenantContextHolder tenantContextHolder;
    private final JobService jobService;

    @Override
    public Job resolver(TokenInfo token, Job job) {
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
