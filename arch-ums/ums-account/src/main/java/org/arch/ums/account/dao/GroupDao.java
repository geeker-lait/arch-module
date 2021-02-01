package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
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
@Repository
public class GroupDao extends ServiceImpl<GroupMapper, Group> implements CrudDao<Group> {

}