package org.arch.ums.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.user.entity.Job;
import org.arch.ums.user.mapper.JobMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户工作信息(Job) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:07:59
 * @since 1.0.0
 */
@Slf4j
@Repository
public class JobDao extends ServiceImpl<JobMapper, Job> implements CrudDao<Job> {

}