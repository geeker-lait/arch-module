package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.AccountTag;
import code.mapper.AccountTagMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-标签(account_tag)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class AccountTagDao extends ServiceImpl<AccountTagMapper, AccountTag> {

}