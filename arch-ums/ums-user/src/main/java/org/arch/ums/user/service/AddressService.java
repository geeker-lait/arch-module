package org.arch.ums.user.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.arch.ums.user.entity.Address;
import org.arch.ums.user.dao.AddressDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.Long;

import org.arch.framework.crud.CrudService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户地址表(Address) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-03-17 22:31:28
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AddressService extends CrudService<Address, java.lang.Long> {

    private final AddressDao addressDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Address entity = new Address();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Address> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Address::getDeleted, 1);
        return addressDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Address entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Address> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Address::getDeleted, 1);
        // 逻辑删除
        return addressDao.update(updateWrapper);
    }

    /**
     * 批量逻辑删除
     *
     * @param ids 主键集合
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean deleteAllById(@NonNull List<java.lang.Long> ids) {

        LambdaUpdateWrapper<Address> updateWrapper = Wrappers.<Address>lambdaUpdate()
                .eq(Address::getDeleted, 0)
                .in(Address::getId, ids)
                .set(Address::getDeleted, 1);

        // 逻辑删除
        return addressDao.update(updateWrapper);
    }
}
