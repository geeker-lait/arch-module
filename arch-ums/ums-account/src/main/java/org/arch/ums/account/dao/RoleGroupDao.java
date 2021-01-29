package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.RoleGroup;
import org.arch.ums.account.mapper.RoleGroupMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-角色组织或机构(RoleGroup) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:25:24
 * @since 1.0.0
 */
@Slf4j
@Repository
public class RoleGroupDao extends ServiceImpl<RoleGroupMapper, RoleGroup> implements CrudDao<RoleGroup> {

}