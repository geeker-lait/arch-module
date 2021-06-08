package org.arch.ums.member.dao;

import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.member.entity.MemberRights;
import org.arch.ums.member.mapper.MemberRightsMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 会员权益(MemberRights) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:41:20
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberRightsDao extends CrudServiceImpl<MemberRightsMapper, MemberRights> implements CrudDao<MemberRights> {
    private final MemberRightsMapper memberRightsMapper;
}
