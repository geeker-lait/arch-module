package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.ResponseStatusCode;
import org.arch.framework.crud.CrudService;
import org.arch.framework.id.IdKey;
import org.arch.framework.id.IdService;
import org.arch.ums.account.dao.IdentifierDao;
import org.arch.ums.account.dao.NameDao;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.dto.AuthRegRequest;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.entity.Name;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 用户-标识(Identifier) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:04:10
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class IdentifierService extends CrudService<Identifier, Long> {

    private final IdentifierDao identifierDao;
    private final NameDao nameDao;
    private final IdService idService;

    /**
     * 查询 identifiers 是否已经存在.
     * @param identifiers   identifiers 列表
     * @param tenantId      租户 ID
     * @return  identifiers 对应的结果集.
     */
    @NonNull
    @Transactional(readOnly = true)
    public List<Boolean> exists(@NonNull List<String> identifiers, @NonNull Integer tenantId) {
        int size = identifiers.size();

        if (size == 0) {
            return Collections.emptyList();
        }
        List<Boolean> exists = identifierDao.exists(identifiers, tenantId);
        return Optional.ofNullable(exists).orElse(Collections.emptyList());
    }

    /**
     * 根据 identifier 获取用户信息 {@link AuthLoginDto}.
     * @param identifier    用户唯一标识
     * @param tenantId      租户 ID
     * @return  返回用户信息 {@link AuthLoginDto}. 不存在返回 null.
     */
    @Nullable
    @Transactional(readOnly = true)
    public AuthLoginDto loadAccountByIdentifier(@NonNull String identifier, @NonNull Integer tenantId) {
        return identifierDao.findAuthLoginDtoByIdentifier(identifier, tenantId);
    }

    /**
     * 注册用户
     * @param authRegRequest    注册用户参数封装
     * @return  返回 {@link AuthLoginDto}, 注册用户失败返回 null.
     */
    @Nullable
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public AuthLoginDto register(@NonNull AuthRegRequest authRegRequest) {
        final LocalDateTime now = LocalDateTime.now();
        // 构建 identifier
        Identifier identifier = new Identifier();
        Long id = Long.valueOf(idService.generateId(IdKey.UMS_ACCOUNT_IDENTIFIER_ID));
        identifier.setId(id)
                  .setAid(authRegRequest.getAid())
                  .setIdentifier(authRegRequest.getIdentifier())
                  .setCredential(authRegRequest.getCredential())
                  .setAuthorities(authRegRequest.getAuthorities())
                  .setChannelType(authRegRequest.getChannelType())
                  .setTenantId(authRegRequest.getTenantId())
                  .setAppId(null)
                  .setStoreId(null)
                  .setRev(0)
                  .setSt(now)
                  .setDeleted(false);
        // 构建 name
        Name name = new Name();
        name.setId(id)
            .setAccountId(authRegRequest.getAid())
            .setNickName(authRegRequest.getNickName())
            .setAvatar(authRegRequest.getAvatar())
            .setSource(authRegRequest.getSource())
            .setTenantId(authRegRequest.getTenantId())
            .setAppId(null)
            .setStoreId(null)
            .setRev(0)
            .setSt(now)
            .setDeleted(false);

        boolean saveIdentifierResult = identifierDao.save(identifier);
        boolean saveNameResult = nameDao.save(name);
        if (saveIdentifierResult && saveNameResult) {
            return AuthLoginDto.builder()
                               .id(id)
                               .aid(authRegRequest.getAid())
                               .tenantId(authRegRequest.getTenantId())
                               .identifier(authRegRequest.getIdentifier())
                               .credential(authRegRequest.getCredential())
                               .channelType(authRegRequest.getChannelType())
                               .authorities(authRegRequest.getAuthorities())
                               .nickName(authRegRequest.getNickName())
                               .avatar(authRegRequest.getAvatar())
                               .build();
        }

        throw new BusinessException(ResponseStatusCode.FAILED,
                                    new String[]{"tenantId:" + authRegRequest.getTenantId(),
                                                 "username:" + authRegRequest.getIdentifier()},
                                    "用户注册失败");
    }

}