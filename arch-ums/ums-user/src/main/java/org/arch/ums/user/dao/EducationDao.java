package org.arch.ums.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.user.entity.Education;
import org.arch.ums.user.mapper.EducationMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户学历信息(Education) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:04:28
 * @since 1.0.0
 */
@Slf4j
@Repository
public class EducationDao extends ServiceImpl<EducationMapper, Education> implements CrudDao<Education> {

}