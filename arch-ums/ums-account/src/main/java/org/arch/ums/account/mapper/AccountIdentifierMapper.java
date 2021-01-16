package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Select;
import org.arch.ums.account.dto.AuthAccountDto;
import org.arch.ums.account.entity.AccountIdentifier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 用户-标识(account_identifier)数据Mapper
 *
 * @author lait
 * @author YongWu zheng
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface AccountIdentifierMapper extends BaseMapper<AccountIdentifier> {

    /**
     * 根据 identifier 获取用户信息 {@link AuthAccountDto}.
     * @param identifier    用户唯一标识
     * @return  返回用户信息 {@link AuthAccountDto}. 不存在返回 null.
     */
    @Nullable
    @Select(value = "SELECT a.id AS id, a.aid AS aid, a.identifier AS identifier, a.credential AS credential," +
            " a.channel_type AS channel_type, a.authorities AS authorities, an.nick_name AS nick_name, an.avatar AS avatar " +
            " FROM (SELECT ai.* FROM account_identifier AS ai WHERE identifier = #{identifier}) a" +
            " INNER JOIN account_name AS an ON a.aid = an.account_id")
    AuthAccountDto getAccountByIdentifier(@NonNull String identifier);
}
