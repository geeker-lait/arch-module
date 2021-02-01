package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.IdentifierDao;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.entity.Identifier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class IdentifierService extends CrudService<Identifier, java.lang.Long> {

    private final IdentifierDao identifierDao;

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
    public AuthLoginDto loadAccountByIdentifier(@NonNull String identifier, @NonNull Integer tenantId) {
        return identifierDao.findAuthLoginDtoByIdentifier(identifier, tenantId);
    }
}