package org.arch.auth.sso.oauth2.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import top.dcenter.ums.security.core.api.oauth.repository.factory.UsersConnectionRepositoryFactory;
import top.dcenter.ums.security.core.api.oauth.repository.jdbc.UsersConnectionRepository;
import top.dcenter.ums.security.core.oauth.properties.RepositoryProperties;
import top.dcenter.ums.security.core.oauth.repository.jdbc.Auth2JdbcUsersConnectionTokenRepository;

/**
 * 空实现, 目的替换 {@link Auth2JdbcUsersConnectionTokenRepository}.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.3 20:03
 */
@Component
public class InvalidUsersConnectionRepositoryFactory implements UsersConnectionRepositoryFactory {

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(JdbcTemplate auth2UserConnectionJdbcTemplate, TextEncryptor textEncryptor, RepositoryProperties repositoryProperties) {
        return new InvalidUsersConnectionRepository();
    }

}
