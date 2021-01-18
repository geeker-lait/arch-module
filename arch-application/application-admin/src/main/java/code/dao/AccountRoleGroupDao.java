package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.AccountRoleGroup;
import code.mapper.AccountRoleGroupMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-角色组织或机构(account_role_group)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class AccountRoleGroupDao extends ServiceImpl<AccountRoleGroupMapper, AccountRoleGroup> {

}