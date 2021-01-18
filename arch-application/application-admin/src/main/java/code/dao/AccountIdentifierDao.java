package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.AccountIdentifier;
import code.mapper.AccountIdentifierMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-标识(account_identifier)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class AccountIdentifierDao extends ServiceImpl<AccountIdentifierMapper, AccountIdentifier> {

}