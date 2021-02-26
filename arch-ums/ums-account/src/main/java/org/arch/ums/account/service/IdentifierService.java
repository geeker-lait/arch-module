package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.api.IdKey;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.ResponseStatusCode;
import org.arch.framework.crud.CrudService;
import org.arch.framework.id.IdService;
import org.arch.framework.ums.enums.ChannelType;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dao.IdentifierDao;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.dto.AuthRegRequest;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.entity.Name;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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

    /**
     * 逻辑删除时 identifier 需要添加的后缀
     */
    public static final String IDENTIFIER_DELETED_SUFFIX = "_DELETED_";
    /**
     * 逻辑删除时 identifier 需要添加的后缀的分隔符
     */
    public static final String IDENTIFIER_SUFFIX_SEPARATOR = "_";
    private final IdentifierDao identifierDao = (IdentifierDao) crudDao;
    private final NameService nameService;
    private final IdService idService;
    private final TenantContextHolder tenantContextHolder;

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
        boolean saveNameResult = nameService.save(name);
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

    /**
     * 逻辑删除(执行 account_identifier 与 account_name 的逻辑删除):<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * & account_name:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     * @param tenantId      租户 ID
     * @param identifier    {@link Identifier}
     * @return  是否删除成功.
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @NotNull
    public boolean logicDeletedByIdentifier(@NonNull Integer tenantId, @NonNull Identifier identifier) {
        // 校验合法性
        if (isNotCurrentUser(tenantId, identifier)) {
            return false;
        }

        // 逻辑删除
        boolean booleanIdentifier = logicDeleted(tenantId, identifier);

        boolean booleanName = nameService.deleteById(identifier.getId());
        if (!booleanIdentifier || !booleanName) {
            throw new RuntimeException("逻辑删除失败: " + identifier);
        }
        return true;
    }

    /**
     * 逻辑删除(执行 account_identifier 与 account_name 的逻辑删除):<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * & account_name:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     * 注意: 此方法会判断删除的 identifier 是否属于当前用户.
     * @param tenantId      租户 ID
     * @param identifier    {@link Identifier}
     * @return  是否删除成功.
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @NotNull
    public Response<Boolean> logicDeletedByIdentifier(@NonNull Integer tenantId, @NonNull String identifier) {
        // 获取 Identifier
        Map<String, Object> params = new LinkedHashMap<>(3, 1.F);
        params.put("tenant_id", tenantId);
        params.put("identifier", identifier);
        params.put("channel_type", ChannelType.OAUTH2);
        params.put("deleted", 0);

        Wrapper<Identifier> queryWrapper = Wrappers.<Identifier>query().allEq(params);
        Identifier accountIdentifier = findOneBySpec(queryWrapper);
        if (isNull(accountIdentifier)) {
            return Response.success(Boolean.FALSE, identifier + " 账号不存在");
        }

        Long id = accountIdentifier.getId();
        Long identifierId = SecurityUtils.getCurrentUser().getIdentifierId();
        if (!identifierId.equals(id)) {
            log.error("删除用户 id 与 当前用户 id 不匹配: tenantId: {}, id: {}, currentUserId: {}",
                      tenantContextHolder.getTenantId(), id, identifierId);
            return Response.success(Boolean.FALSE, "非法操作");
        }

        // 逻辑删除
        return Response.success(logicDeletedByIdentifier(tenantId, accountIdentifier));
    }

    /**
     * 解绑 identifier :<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * @param tenantId      租户 ID
     * @param identifier    {@link Identifier}
     * @return  是否解绑成功.
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @NotNull
    public boolean unbinding(@NonNull Integer tenantId, @NonNull Identifier identifier) {
        if (isNotCurrentUser(tenantId, identifier)) {
            return false;
        }

        // 逻辑删除
        boolean booleanIdentifier = logicDeleted(tenantId, identifier);
        if (!booleanIdentifier) {
            throw new RuntimeException("解绑失败: " + identifier);
        }
        return true;
    }

    /**
     * 根据 aid 解绑 identifier(第三方标识) :<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * @param identifier    {@link Identifier#getIdentifier()}
     * @param aid           {@link Identifier#getAid()}
     * @return  是否解绑成功.
     */
    @NonNull
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Boolean unbinding(String identifier, Long aid) {
        Integer tenantId = Integer.valueOf(tenantContextHolder.getTenantId());
        // 获取 Identifier
        Map<String, Object> params = new LinkedHashMap<>(5, 1.F);
        params.put("tenant_id", tenantId);
        params.put("identifier", identifier);
        params.put("aid", aid);
        params.put("channel_type", ChannelType.OAUTH2);
        params.put("deleted", 0);
        Wrapper<Identifier> queryWrapper = Wrappers.<Identifier>query().allEq(params);
        Identifier accountIdentifier = findOneBySpec(queryWrapper);

        if (isNull(accountIdentifier)) {
            return Boolean.TRUE;
        }

        // 解绑
        return unbinding(tenantId, accountIdentifier);
    }

    /**
     * 逻辑删除(执行 account_identifier 与 account_name 的逻辑删除):<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * & account_name:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     * @param id    {@link Identifier#getId()}
     * @return  是否删除成功.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean deleteById(Long id) {
        Integer tenantId = Integer.valueOf(tenantContextHolder.getTenantId());
        // 获取 Identifier
        Map<String, Object> params = new LinkedHashMap<>(2);
        params.put("id", id);
        params.put("tenant_id", tenantId);
        params.put("deleted", 0);

        Wrapper<Identifier> queryWrapper = Wrappers.<Identifier>query().allEq(params);
        Identifier identifier = findOneBySpec(queryWrapper);
        if (isNull(identifier)) {
        	return false;
        }
        return logicDeletedByIdentifier(tenantId, identifier);
    }

    /**
     * 逻辑删除(执行 account_identifier 与 account_name 的逻辑删除):<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * & account_name:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     * @param identifier    {@link Identifier}
     * @return  是否删除成功.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean deleteById(Identifier identifier) {
        // 获取 Identifier
        Identifier accountIdentifier = crudDao.getOne(new QueryWrapper<>(identifier));
        if (isNull(accountIdentifier)) {
            return false;
        }
        // 逻辑删除
        return logicDeletedByIdentifier(identifier.getTenantId(), accountIdentifier);
    }

    /**
     * 逻辑删除(执行 account_identifier 与 account_name 的逻辑删除):<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * & account_name:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     * @param ids    ids
     * @return  是否删除成功.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean deleteAllById(List<Long> ids) {
        Integer tenantId = Integer.valueOf(tenantContextHolder.getTenantId());
        // 获取 Identifier
        Wrapper<Identifier> queryWrapper =
                Wrappers.<Identifier>lambdaQuery()
                        .in(Identifier::getId, ids)
                        .and(i -> i.eq(Identifier::getTenantId, tenantId))
                        .and(i -> i.eq(Identifier::getDeleted, 0));
        List<Identifier> identifierList = findAllBySpec(queryWrapper);
        if (isNull(identifierList) || identifierList.size() == 0) {
            return false;
        }
        // 逻辑删除, 待优化
        for (Identifier identifier : identifierList) {
            logicDeletedByIdentifier(tenantId, identifier);
        }
        return true;
    }

    /**
     * identifier 是否当为前用户
     * @param tenantId      租户 ID
     * @param identifier    {@link Identifier}
     * @return  true 表示是当前用户, false 表示不是当前用户
     */
    private boolean isNotCurrentUser(@NonNull Integer tenantId, @NonNull Identifier identifier) {
        // 校验合法性
        Long accountId = SecurityUtils.getCurrentUserId();
        if (!(tenantId.equals(identifier.getTenantId()) && accountId.equals(identifier.getAid()))) {
            log.warn("非法删除: 操作用户: {}, 目标租户: {}, 目标账号标识: {}",
                     identifier, tenantId, identifier.getIdentifier());
            return true;
        }
        return false;
    }

    /**
     * 逻辑删除 identifier
     * @param tenantId      租户 ID
     * @param identifier    {@link Identifier}
     * @return  true 表示逻辑删除成功, false 表示逻辑删除失败
     */
    private boolean logicDeleted(@NonNull Integer tenantId, @NonNull Identifier identifier) {
        // 查询是否有最近的历史删除记录
        Identifier deletedIdentifier =
                identifierDao.selectLogicDeleted(tenantId,
                                                 identifier.getIdentifier() + IDENTIFIER_DELETED_SUFFIX + "%");
        // 有则提取删除序号
        final int seq;
        if (nonNull(deletedIdentifier)) {
            String[] splits = deletedIdentifier.getIdentifier().split(IDENTIFIER_SUFFIX_SEPARATOR);
            seq = Integer.parseInt(splits[splits.length - 1]) + 1;
        }
        else {
            seq = 0;
        }

        // 逻辑删除, likeIdentifierPrefix(防止用户重新通过此第三方注册时触发唯一索引问题), seq(防止多次删除同一个第三方账号时触发唯一索引问题)
        return identifierDao.logicDeleted(identifier.getId(), IDENTIFIER_DELETED_SUFFIX + seq);
    }
}