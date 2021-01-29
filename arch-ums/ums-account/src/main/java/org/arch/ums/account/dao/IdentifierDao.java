package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.mapper.IdentifierMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户-标识(Identifier) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:03:44
 * @since 1.0.0
 */
@Slf4j
@Repository
public class IdentifierDao extends ServiceImpl<IdentifierMapper, Identifier> implements CrudDao<Identifier> {
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