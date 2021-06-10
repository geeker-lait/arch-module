package org.arch.ums.user.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.user.dto.JobRequest;
import org.arch.ums.user.dto.JobSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户工作信息(Job) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:59:29
 * @since 1.0.0
 */

@RestController
@RequestMapping("/user/job")
public interface JobRest extends CrudRest<JobRequest, java.lang.Long, JobSearchDto> {

}

