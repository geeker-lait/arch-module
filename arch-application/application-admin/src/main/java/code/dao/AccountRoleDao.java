package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.AccountRole;
import code.mapper.AccountRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-角色(account_role)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class AccountRoleDao extends ServiceImpl<AccountRoleMapper, AccountRole> {

}