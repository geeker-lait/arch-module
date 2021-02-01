package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.OauthToken;
import org.arch.ums.account.mapper.OauthTokenMapper;
import org.springframework.stereotype.Repository;

/**
 * 第三方账号授权(OauthToken) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:55:08
 * @since 1.0.0
 */
@Slf4j
@Repository
public class OauthTokenDao extends ServiceImpl<OauthTokenMapper, OauthToken> implements CrudDao<OauthToken> {

}