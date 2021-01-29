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

/**
 * 用户工作信息(Job) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:08:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/job")
public class JobController implements CrudController<Job, java.lang.Long, JobSearchDto, JobService> {

    private final JobService jobService;

    @Override
    public Job resolver(TokenInfo token, Job job) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 job 后返回 job, 如: tenantId 的处理等.
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