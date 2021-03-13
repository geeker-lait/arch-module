package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.Relationship;
import org.arch.ums.account.mapper.RelationshipMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-关系(Relationship) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:20:31
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class RelationshipDao extends CrudServiceImpl<RelationshipMapper, Relationship> implements CrudDao<Relationship> {
    private final RelationshipMapper relationshipMapper;
}