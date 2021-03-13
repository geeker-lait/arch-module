package org.arch.ums.user.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
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
@RequiredArgsConstructor
@Repository
public class EducationDao extends CrudServiceImpl<EducationMapper, Education> implements CrudDao<Education> {
    private final EducationMapper educationMapper;
}