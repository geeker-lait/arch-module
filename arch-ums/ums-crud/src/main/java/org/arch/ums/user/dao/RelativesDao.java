package org.arch.ums.user.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.user.entity.Relatives;
import org.arch.ums.user.mapper.RelativesMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户亲朋信息(Relatives) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:02:20
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class RelativesDao extends CrudServiceImpl<RelativesMapper, Relatives> implements CrudDao<Relatives> {
    private final RelativesMapper relativesMapper;
}