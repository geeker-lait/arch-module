package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.AccountCategory;
import code.mapper.AccountCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-资源类目(account_category)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class AccountCategoryDao extends ServiceImpl<AccountCategoryMapper, AccountCategory> {

}