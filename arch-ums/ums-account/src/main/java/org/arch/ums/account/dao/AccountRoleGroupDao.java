package org.arch.ums.account.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.entity.AccountRoleGroup;
import org.arch.ums.account.mapper.AccountRoleGroupMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-角色组织或机构(account_role_group)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class AccountRoleGroupDao extends ServiceImpl<AccountRoleGroupMapper, AccountRoleGroup> {

}