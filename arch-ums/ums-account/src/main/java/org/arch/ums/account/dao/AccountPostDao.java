package org.arch.ums.account.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.entity.AccountPost;
import org.arch.ums.account.mapper.AccountPostMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 账号-岗位(account_post)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class AccountPostDao extends ServiceImpl<AccountPostMapper, AccountPost> {

}