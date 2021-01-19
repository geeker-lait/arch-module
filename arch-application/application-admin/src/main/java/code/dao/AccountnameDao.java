package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.Accountname;
import code.mapper.AccountnameMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-名称(accountname)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class AccountnameDao extends ServiceImpl<AccountnameMapper, Accountname> {

}