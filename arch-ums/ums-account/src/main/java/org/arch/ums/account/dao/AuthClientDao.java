package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.account.mapper.AuthClientMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * 授权客户端(AuthClient) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:47:57
 * @since 1.0.0
 */
@Slf4j
@Repository
public class AuthClientDao extends ServiceImpl<AuthClientMapper, AuthClient> implements CrudDao<AuthClient> {

//    /**
//     * 根据 clientId 与 clientSecret 获取 scopes
//     * @param appId     app id
//     * @param appCode   app code
//     * @return  返回 scope 字符串, 可能未 null
//     */
//    @Nullable
//    public String getScopes(@NonNull String appId, @NonNull String appCode) {
//        return accountOauthClientMapper.getScopes(appId, appCode);
//    }
}