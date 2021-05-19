package org.arch.admin.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.feign.FeignCrudController;
import org.arch.ums.user.dto.JobRequest;
import org.arch.ums.user.dto.JobSearchDto;
import org.arch.ums.user.client.UserJobFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户工作信息(Job) CRUD 控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 19:40:56
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController("adminJobController")
@RequestMapping("/user/job")
public class JobController implements FeignCrudController<JobSearchDto, java.lang.Long, JobRequest, UserJobFeignService> {

    private final UserJobFeignService userJobService;

    @Override
    public UserJobFeignService getFeignService() {
        return this.userJobService;
    }

}
