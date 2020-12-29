package org.arch.ums.account.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.entity.AccountRelationship;
import org.arch.ums.account.mapper.AccountRelationshipMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-关系(account_relationship)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class AccountRelationshipDao extends ServiceImpl<AccountRelationshipMapper, AccountRelationship> {

}