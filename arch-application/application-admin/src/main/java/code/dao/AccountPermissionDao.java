package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.AccountPermission;
import code.mapper.AccountPermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-权限(account_permission)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class AccountPermissionDao extends ServiceImpl<AccountPermissionMapper, AccountPermission> {

}