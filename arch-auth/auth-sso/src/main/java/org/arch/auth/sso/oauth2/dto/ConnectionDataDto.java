package org.arch.auth.sso.oauth2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.arch.ums.account.entity.Identifier;
import top.dcenter.ums.security.core.api.oauth.entity.ConnectionData;

/**
 * {@link ConnectionData} DTO
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.25 13:17
 */
@Setter
@Getter
@NoArgsConstructor
public class ConnectionDataDto extends ConnectionData {
    private static final long serialVersionUID = -2897696749582077912L;

    /**
     * {@link Identifier#getId()}
     */
    private Long identifierId;
}
