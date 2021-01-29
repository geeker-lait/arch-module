package org.arch.ums.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.account.entity.AuthClient;

/**
 * 授权客户端(AuthClient) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:48:12
 * @since 1.0.0
 */
@Mapper
public interface AuthClientMapper extends BaseMapper<AuthClient> {

//    /**
//     * 根据 clientId 与 clientSecret 获取 scopes
//     * @param appId     app id
//     * @param appCode   app code
//     * @return  返回 scopes
//     */
//    @Nullable
//    @Select(value = "SELECT scopes FROM account_oauth_client WHERE app_id = #{clientId} AND app_code = #{clientSecret}")
//    String getScopes(@NonNull @Param("clientId") String appId, @NonNull @Param("clientSecret") String appCode);
}