package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.Member;
import org.arch.ums.account.mapper.MemberMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-会员账号(Member) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:11:45
 * @since 1.0.0
 */
@Slf4j
@Repository
public class MemberDao extends ServiceImpl<MemberMapper, Member> implements CrudDao<Member> {

}