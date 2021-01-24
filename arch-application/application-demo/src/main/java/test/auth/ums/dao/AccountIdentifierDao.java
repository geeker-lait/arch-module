package test.auth.ums.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;
import test.auth.ums.entity.AccountIdentifier;
import test.auth.ums.mapper.AccountIdentifierMapper;

/**
 * 用户-标识(AccountIdentifier) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-24 23:17:40
 * @since 1.0.0
 */
@Slf4j
@Repository
public class AccountIdentifierDao extends ServiceImpl<AccountIdentifierMapper, AccountIdentifier> implements CrudDao<AccountIdentifier> {

}