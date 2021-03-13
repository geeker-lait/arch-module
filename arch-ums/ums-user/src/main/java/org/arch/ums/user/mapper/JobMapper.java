package org.arch.ums.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.user.entity.Job;

/**
 * 用户工作信息(Job) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:08:07
 * @since 1.0.0
 */
@Mapper
public interface JobMapper extends CrudMapper<Job> {

}