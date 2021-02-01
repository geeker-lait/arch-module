package org.arch.ums.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.user.entity.Job;
import org.springframework.stereotype.Service;

/**
 * 用户工作信息(Job) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:08:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JobService extends CrudService<Job, java.lang.Long> {

}