package org.arch.ums.account.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.entity.AccountRoleResource;
import org.arch.ums.account.mapper.AccountRoleResourceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-角色资源表(account_role_resource)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class AccountRoleResourceDao extends ServiceImpl<AccountRoleResourceMapper, AccountRoleResource> {

}