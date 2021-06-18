package org.arch.ums.member.dao;

import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.member.entity.MemberLevel;
import org.arch.ums.member.mapper.MemberLevelMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 会员级别(MemberLevel) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:41:15
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberLevelDao extends CrudServiceImpl<MemberLevelMapper, MemberLevel> implements CrudDao<MemberLevel> {
    private final MemberLevelMapper memberLevelMapper;
}
