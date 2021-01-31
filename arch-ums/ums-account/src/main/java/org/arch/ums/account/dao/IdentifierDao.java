package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.mapper.IdentifierMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * 用户-标识(Identifier) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:03:44
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class IdentifierDao extends ServiceImpl<IdentifierMapper, Identifier> implements CrudDao<Identifier> {

    private final IdentifierMapper identifierMapper;

    /**
     * 查询 identifiers 是否已经存在.
     * @param identifiers    identifiers 列表
     * @return  返回 identifiers 对应的结果集.
     */
    @Nullable
    public List<Boolean> exists(@NonNull List<String> identifiers, @NonNull Integer tenantId) {

        int size = identifiers.size();
        if (size == 0) {
            return Collections.emptyList();
        }
        if (size == 1) {
            LambdaQueryWrapper<Identifier> wrapper = Wrappers.lambdaQuery();
            wrapper = wrapper.eq(Identifier::getTenantId, tenantId)
                             .eq(Identifier::getIdentifier, identifiers.get(0));

            Integer count = identifierMapper.selectCount(wrapper);
            return Collections.singletonList(count > 0);
        }
        else {
            return identifierMapper.exists(identifiers, tenantId);
        }

    }


//    /**
//     * 根据 identifier 获取用户信息 {@link AuthAccountDto}.
//     * @param identifier    用户唯一标识
//     * @return  返回用户信息 {@link AuthAccountDto}. 不存在返回 null.
//     */
//    @Nullable
//    public AuthAccountDto getAccountByIdentifier(@NonNull String identifier) {
//        return accountIdentifierMapper.getAccountByIdentifier(identifier);
//    }
}