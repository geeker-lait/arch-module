package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.arch.ums.account.entity.AccountOauthClient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 授权客户端(account_oauth_client)数据Mapper
 *
 * @author lait
 * @author YongWu zheng
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface AccountOauthClientMapper extends BaseMapper<AccountOauthClient> {

    /**
     * 根据 appId 与 appCode 获取 scopes
     * @param appId     app id
     * @param appCode   app code
     * @return  返回 scopes
     */
    @Nullable
    @Select(value = "SELECT scopes FROM account_oauth_client WHERE app_id = #{appId} AND app_code = #{appCode}")
    String getScopes(@NonNull @Param("appId") String appId, @NonNull @Param("appCode") String appCode);
}
