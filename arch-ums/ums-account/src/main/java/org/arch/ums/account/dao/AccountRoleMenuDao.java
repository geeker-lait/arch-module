package org.arch.ums.account.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.entity.AccountRoleMenu;
import org.arch.ums.account.mapper.AccountRoleMenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-角色菜单(account_role_menu)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class AccountRoleMenuDao extends ServiceImpl<AccountRoleMenuMapper, AccountRoleMenu> {

}