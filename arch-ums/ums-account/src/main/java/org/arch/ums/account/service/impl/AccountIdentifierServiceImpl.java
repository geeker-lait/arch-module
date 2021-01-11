package org.arch.ums.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dao.AccountIdentifierDao;
import org.arch.ums.account.entity.AccountIdentifier;
import org.arch.ums.account.service.AccountIdentifierService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * 用户-标识服务接口实现
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountIdentifierServiceImpl implements AccountIdentifierService {
    private final AccountIdentifierDao accountIdentifierDao;

    @Override
    public boolean saveBatch(Collection<AccountIdentifier> entityList, int batchSize) {
        // TODO
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<AccountIdentifier> entityList, int batchSize) {
        // TODO
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<AccountIdentifier> entityList, int batchSize) {
        // TODO
        return false;
    }

    @Override
    public boolean saveOrUpdate(AccountIdentifier entity) {
        // TODO
        return false;
    }

    @Override
    public AccountIdentifier getOne(Wrapper<AccountIdentifier> queryWrapper, boolean throwEx) {
        // TODO
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<AccountIdentifier> queryWrapper) {
        // TODO
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<AccountIdentifier> queryWrapper, Function<? super Object, V> mapper) {
        // TODO
        return null;
    }

    @Override
    public BaseMapper<AccountIdentifier> getBaseMapper() {
        // TODO
        return null;
    }
}