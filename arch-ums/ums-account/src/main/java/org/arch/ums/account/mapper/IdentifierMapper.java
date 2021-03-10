package org.arch.ums.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.entity.Identifier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 用户-标识(Identifier) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:04:39
 * @since 1.0.0
 */
@Mapper
public interface IdentifierMapper extends BaseMapper<Identifier> {

    /**
     * 查询 identifiers 是否已经存在.
     * @param identifiers  identifier 列表
     * @param tenantId     租户 ID
     * @return  identifiers 对应的结果集.
     */
    @Nullable
    List<Boolean> exists(@NonNull @Param("identifiers") List<String> identifiers,
                         @NonNull @Param("tenantId") Integer tenantId);

    /**
     * 根据 identifier 获取用户信息 {@link AuthLoginDto}.
     * @param identifier    用户唯一标识
     * @param tenantId      租户 ID
     * @return  返回用户信息 {@link AuthLoginDto}. 不存在返回 null.
     */
    @Nullable
    @Select(value = "SELECT a.id AS id, a.aid AS aid, a.identifier AS identifier, a.credential AS credential, a.tenant_id AS tenantId," +
            " a.login_type AS loginType, a.authorities AS authorities, an.nick_name AS nickName, an.avatar AS avatar " +
            " FROM (SELECT ai.* FROM account_identifier AS ai " +
            "       WHERE tenant_id = #{tenantId} AND identifier = #{identifier} AND `deleted` = 0 LIMIT 1) a" +
            " INNER JOIN account_name AS an ON a.aid = an.account_id AND a.tenant_id = an.tenant_id")
    AuthLoginDto findAuthLoginDtoByIdentifier(@NonNull @Param("identifier") String identifier,
                                              @NonNull @Param("tenantId") Integer tenantId);

    /**
     * 查询是否有最近的历史删除记录
     * @param tenantId          租户 ID
     * @param likeIdentifier    identifier 字段的 like 字符串
     * @return  返回最近的逻辑删除的 {@link Identifier}, 如果没有则返回 null
     */
    @Nullable
    @Select(value = "SELECT * FROM account_identifier " +
            " WHERE tenant_id = #{tenantId} AND identifier like #{identifier} AND deleted = 1 ORDER BY dt DESC LIMIT 1")
    Identifier selectLogicDeleted(@NonNull @Param("tenantId") Integer tenantId,
                                  @NonNull @Param("identifier") String likeIdentifier);

    /**
     * 逻辑删除
     * @param id                租户 ID
     * @param identifierSuffix  _DELETED_0, _DELETED_0(防止用户重新通过此第三方注册时触发唯一索引问题), 0(防止多次删除同一个第三方账号时触发唯一索引问题).
     * @return  是否删除成功
     */
    @Nullable
    @Update(value = "UPDATE account_identifier SET identifier = CONCAT(identifier, #{identifierSuffix}), deleted = 1" +
            " WHERE id = #{id} LIMIT 1")
    int logicDeleted(@NonNull @Param("id") Long id, @NonNull @Param("identifierSuffix") String identifierSuffix);
}