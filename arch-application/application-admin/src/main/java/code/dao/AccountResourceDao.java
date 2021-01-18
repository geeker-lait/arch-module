package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.AccountResource;
import code.mapper.AccountResourceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-资源(account_resource)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class AccountResourceDao extends ServiceImpl<AccountResourceMapper, AccountResource> {

}