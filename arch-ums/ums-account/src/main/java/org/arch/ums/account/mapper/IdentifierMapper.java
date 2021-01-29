package org.arch.ums.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.account.entity.Identifier;

/**
 * 用户-标识(Identifier) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:04:39
 * @since 1.0.0
 */
@Mapper
public interface IdentifierMapper extends BaseMapper<Identifier> {

//    /**
//     * 根据 identifier 获取用户信息 {@link AuthAccountDto}.
//     * @param identifier    用户唯一标识
//     * @return  返回用户信息 {@link AuthAccountDto}. 不存在返回 null.
//     */
//    @Nullable
//    @Select(value = "SELECT a.id AS id, a.aid AS aid, a.identifier AS identifier, a.credential AS credential," +
//            " a.channel_type AS channel_type, a.authorities AS authorities, an.nick_name AS nick_name, an.avatar AS avatar " +
//            " FROM (SELECT ai.* FROM account_identifier AS ai WHERE identifier = #{identifier}) a" +
//            " INNER JOIN account_name AS an ON a.aid = an.account_id")
//    AuthAccountDto getAccountByIdentifier(@NonNull @Param("identifier") String identifier);
}