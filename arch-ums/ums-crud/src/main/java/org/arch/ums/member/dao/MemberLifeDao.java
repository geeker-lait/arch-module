package org.arch.ums.member.dao;

import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.member.entity.MemberLife;
import org.arch.ums.member.mapper.MemberLifeMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 会员生命周期(MemberLife) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:41:18
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberLifeDao extends CrudServiceImpl<MemberLifeMapper, MemberLife> implements CrudDao<MemberLife> {
    private final MemberLifeMapper memberLifeMapper;
}
