package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.Group;
import org.arch.ums.account.mapper.GroupMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-组织机构(Group) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:01:00
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class GroupDao extends CrudServiceImpl<GroupMapper, Group> implements CrudDao<Group> {
    private final GroupMapper groupMapper;
}