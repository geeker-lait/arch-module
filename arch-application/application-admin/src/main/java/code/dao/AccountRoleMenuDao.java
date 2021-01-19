package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.AccountRoleMenu;
import code.mapper.AccountRoleMenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-角色菜单(account_role_menu)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class AccountRoleMenuDao extends ServiceImpl<AccountRoleMenuMapper, AccountRoleMenu> {

}