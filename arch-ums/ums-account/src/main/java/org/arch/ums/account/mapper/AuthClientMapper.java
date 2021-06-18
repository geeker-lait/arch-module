package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.account.vo.AuthClientVo;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 授权客户端(AuthClient) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:48:12
 * @since 1.0.0
 */
@Mapper
public interface AuthClientMapper extends CrudMapper<AuthClient> {

    /**
     * 根据 clientId 与 clientSecret 查询 scopes
     *
     * @param tenantId      租户 ID
     * @param clientId      client id
     * @param clientSecret  client secret
     * @return  返回 scopes 字符串, 可能为 null.
     */
    @Nullable
    @Select(value = "SELECT `scopes` FROM `account_auth_client` " +
            "WHERE tenant_id = #{tenantId} AND client_id = #{clientId} AND client_secret = #{clientSecret} " +
            "AND deleted = 0 LIMIT 1")
    String getScopesByClientIdAndClientSecret(@NonNull @Param("tenantId") Integer tenantId,
                                              @NonNull @Param("clientId") String clientId,
                                              @NonNull @Param("clientSecret") String clientSecret);

    /**
     * 获取所有租户的 scopes
     *
     * @return scopes
     */
    @Nullable
    @Select(value = "SELECT `tenant_id` as tenantId, `client_id` as clientId, `client_secret` as clientSecret, " +
            "`scopes`, `role_ids` as roleIds FROM `account_auth_client` WHERE deleted = 0")
    List<AuthClientVo> getAllScopes();

}