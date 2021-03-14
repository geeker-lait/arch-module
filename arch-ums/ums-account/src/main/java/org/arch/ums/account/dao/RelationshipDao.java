package org.arch.ums.account.dao;

import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.Relationship;
import org.arch.ums.account.mapper.RelationshipMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 账号-关系(Relationship) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-03-14 14:36:56
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class RelationshipDao extends CrudServiceImpl<RelationshipMapper, Relationship> implements CrudDao<Relationship> {
    private final RelationshipMapper relationshipMapper;
}
