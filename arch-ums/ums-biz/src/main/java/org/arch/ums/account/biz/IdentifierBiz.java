package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.api.IdKey;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.ResponseStatusCode;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.id.IdService;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.consts.AccountConstants;
import org.arch.framework.ums.enums.LoginType;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dao.IdentifierDao;
import org.arch.ums.account.dto.Auth2ConnectionDto;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.dto.AuthRegRequest;
import org.arch.ums.account.dto.IdentifierRequest;
import org.arch.ums.account.dto.IdentifierSearchDto;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.entity.Name;
import org.arch.ums.account.rest.IdentifierRest;
import org.arch.ums.account.service.IdentifierService;
import org.arch.ums.account.service.NameService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.jwt.JwtContext;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 用户-标识(Identifier) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:02
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class IdentifierBiz implements CrudBiz<IdentifierRequest, Identifier, java.lang.Long, IdentifierSearchDto, IdentifierSearchDto, IdentifierService>, IdentifierRest {

    /**
     * 逻辑删除时 identifier 需要添加的后缀
     */
    public static final String IDENTIFIER_DELETED_SUFFIX = "_DELETED_";
    /**
     * 逻辑删除时 identifier 需要添加的后缀的分隔符
     */
    public static final String IDENTIFIER_SUFFIX_SEPARATOR = "_";

    private final TenantContextHolder tenantContextHolder;
    private final IdentifierService identifierService;
    private final NameService nameService;
    private final IdentifierDao identifierDao;
    private final IdService idService;

    @Override
    public Identifier resolver(TokenInfo token, IdentifierRequest request) {
        Identifier identifier = new Identifier();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, identifier);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            identifier.setTenantId(token.getTenantId());
        }
        else {
            identifier.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return identifier;
    }

    @Override
    public IdentifierService getCrudService() {
        return identifierService;
    }

    @Override
    public IdentifierSearchDto getSearchDto() {
        return new IdentifierSearchDto();
    }

    /**
     * 查询 identifiers 是否已经存在.
     * @param identifiers    identifiers 列表
     * @return  identifiers 对应的结果集.
     */
    @Override
    @NonNull
    public List<Boolean> exists(List<String> identifiers) {
        String tenantId = tenantContextHolder.getTenantId();
        int size = identifiers.size();
        if (size == 0) {
            return Collections.emptyList();
        }
        List<Boolean> exists = identifierDao.exists(identifiers, Integer.valueOf(tenantId));
        return Optional.ofNullable(exists).orElse(Collections.emptyList());
    }


    /**
     * 根据 identifier 获取用户信息 {@link AuthLoginDto}.
     * @param identifier    用户唯一标识
     * @return  返回用户信息 {@link AuthLoginDto}. 不存在返回 null.
     */
    @Override
    @NonNull
    public AuthLoginDto loadAccountByIdentifier(String identifier) {

        AuthLoginDto authLoginDto = identifierDao.findAuthLoginDtoByIdentifier(identifier,
                                                                               Integer.valueOf(tenantContextHolder.getTenantId()));
        if (isNull(authLoginDto)) {
            throw new BusinessException("用户注册失败");
        }

        return authLoginDto;
    }

    /**
     * 注册用户
     * @param authRegRequest    注册用户参数封装
     * @return  返回 {@link AuthLoginDto}, 注册失败 {@link Response#getData()} 返回 null.
     */
    @Override
    @NonNull
    public AuthLoginDto register(AuthRegRequest authRegRequest) {
        final LocalDateTime now = LocalDateTime.now();
        // 构建 identifier
        Identifier identifier = new Identifier();
        Long id = Long.valueOf(idService.generateId(IdKey.UMS_ACCOUNT_IDENTIFIER_ID));
        identifier.setId(id)
                  .setAid(authRegRequest.getAid())
                  .setIdentifier(authRegRequest.getIdentifier())
                  .setCredential(authRegRequest.getCredential())
                  .setAuthorities(authRegRequest.getAuthorities())
                  .setLoginType(authRegRequest.getLoginType())
                  .setTenantId(authRegRequest.getTenantId())
                  .setAppId(null)
                  .setStoreId(null)
                  .setRev(0)
                  .setDt(now)
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
            .setDt(now)
            .setDeleted(false);

        boolean saveIdentifierResult = identifierDao.save(identifier);
        boolean saveNameResult = nameService.save(name);
        if (saveIdentifierResult && saveNameResult) {
            return AuthLoginDto.builder()
                               .id(id)
                               .aid(authRegRequest.getAid())
                               .tenantId(authRegRequest.getTenantId())
                               .identifier(authRegRequest.getIdentifier())
                               .credential("[PROTECTED]")
                               .loginType(authRegRequest.getLoginType())
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
     * 注意: 此方法会判断删除的 identifier 是否属于当前用户.
     * @param identifier    {@link Identifier}
     * @return  是否删除成功.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @NonNull
    public Boolean logicDeleteByIdentifier(@NonNull String identifier) {
        String tenantId = tenantContextHolder.getTenantId();
        // 获取 Identifier
        Map<String, Object> params = new LinkedHashMap<>(3, 1.F);
        params.put("tenant_id", Integer.valueOf(tenantId));
        params.put("identifier", identifier);
        params.put("deleted", 0);

        Wrapper<Identifier> queryWrapper = Wrappers.<Identifier>query().allEq(params);
        Identifier accountIdentifier = identifierService.findOneBySpec(queryWrapper);
        if (isNull(accountIdentifier)) {
            throw new BusinessException(identifier + " 账号不存在");
        }

        Long id = accountIdentifier.getId();
        Long identifierId = SecurityUtils.getCurrentUser().getIdentifierId();
        if (!identifierId.equals(id)) {
            String msg = String.format("删除用户 id 与 当前用户 id 不匹配: tenantId: %s id: %s, currentUserId: %s",
                                       tenantId, id, identifierId);
            throw new BusinessException(msg);
        }

        // 逻辑删除
        return logicDeletedByIdentifier(Integer.valueOf(tenantId), accountIdentifier);
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
    @Override
    @NonNull
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Boolean unbinding(Long aid, String identifier) {
        Integer tenantId = Integer.valueOf(tenantContextHolder.getTenantId());
        // 获取 Identifier
        Map<String, Object> params = new LinkedHashMap<>(5, 1.F);
        params.put("tenant_id", tenantId);
        params.put("identifier", identifier);
        params.put("aid", aid);
        params.put("login_type", LoginType.OAUTH2.ordinal());
        params.put("deleted", 0);
        Wrapper<Identifier> queryWrapper = Wrappers.<Identifier>query().allEq(params);
        Identifier accountIdentifier = identifierService.findOneBySpec(queryWrapper);

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
    public Boolean deleteById(Long id) {
        Integer tenantId;
        TokenInfo currentUser = SecurityUtils.getCurrentUser();
        tenantId = currentUser.getTenantId();
        Long identifierId = currentUser.getIdentifierId();
        if (!identifierId.equals(id)) {
            log.error("删除用户 id 与 当前用户 id 不匹配: tenantId: {}, id: {}, currentUserId: {}",
                      tenantId, id, identifierId);
            return Boolean.FALSE;
        }
        // 逻辑删除
        boolean deleteByIdResult = identifierService.deleteById(id);
        if (deleteByIdResult) {
            JwtContext.addReAuthFlag(SecurityUtils.getCurrentUserId().toString());
            SecurityContextHolder.clearContext();
        }
        return deleteByIdResult;
    }

    /**
     * 删除账号
     * @param accountId 账号ID/用户ID/会员ID/商户ID
     * @return  true 表示成功, false 表示失败
     */
    @Override
    @NonNull
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Boolean deleteByAccountId(@NonNull Long accountId) {
        String tenantId = tenantContextHolder.getTenantId();
        LambdaQueryWrapper<Identifier> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Identifier::getTenantId, Integer.valueOf(tenantId))
                    .eq(Identifier::getAid, accountId)
                    .eq(Identifier::getDeleted, Boolean.FALSE);
        List<Identifier> identifierList = identifierService.findAllBySpec(queryWrapper);
        List<Long> ids = identifierList.stream().map(Identifier::getId).collect(Collectors.toList());
        return deleteAllById(ids);
    }

    /**
     * 查询 accountId 下所有的第三方绑定账号
     *
     * @param accountId 账号ID/用户ID/会员ID/商户ID
     * @return 绑定账号集合
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Map<String, List<Auth2ConnectionDto>> listAllConnections(@NonNull Long accountId) {
        String tenantId = tenantContextHolder.getTenantId();
        LambdaQueryWrapper<Identifier> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Identifier::getTenantId, Integer.valueOf(tenantId))
                    .eq(Identifier::getAid, accountId)
                    .eq(Identifier::getLoginType, LoginType.OAUTH2.ordinal())
                    .eq(Identifier::getDeleted, Boolean.FALSE)
                    .select(Identifier::getId, Identifier::getAid, Identifier::getIdentifier);
        List<Identifier> identifierList = identifierService.findAllBySpec(queryWrapper);


        return identifierList.stream()
                             .map(identifier -> Auth2ConnectionDto.builder()
                                                                  .id(identifier.getId())
                                                                  .aid(identifier.getAid())
                                                                  .identifier(identifier.getIdentifier())
                                                                  .build())
                             .collect(Collectors.groupingBy(dto -> {
                                 String identifier = dto.getIdentifier();
                                 int indexOf = identifier.indexOf(AccountConstants.OAUTH_IDENTIFIER_SEPARATOR);
                                 return identifier.substring(0, indexOf);
                             }));
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
    public Boolean deleteAllById(List<Long> ids) {
        Integer tenantId = Integer.valueOf(tenantContextHolder.getTenantId());
        // 获取 Identifier
        Wrapper<Identifier> queryWrapper =
                Wrappers.<Identifier>lambdaQuery()
                        .eq(Identifier::getTenantId, tenantId)
                        .in(Identifier::getId, ids)
                        .eq(Identifier::getDeleted, Boolean.FALSE);
        List<Identifier> identifierList = identifierService.findAllBySpec(queryWrapper);
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
    @NonNull
    private boolean unbinding(@NonNull Integer tenantId, @NonNull Identifier identifier) {
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
    @NonNull
    private boolean logicDeletedByIdentifier(@NonNull Integer tenantId, @NonNull Identifier identifier) {
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

        // 逻辑删除, IDENTIFIER_DELETED_SUFFIX(防止用户重新通过此第三方注册时触发唯一索引问题), seq(防止多次删除同一个第三方账号时触发唯一索引问题)
        return identifierDao.logicDeleted(identifier.getId(), IDENTIFIER_DELETED_SUFFIX + seq);
    }


}
