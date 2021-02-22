package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.entity.Name;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号名(Name) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:53:56
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class NameService extends CrudService<Name, java.lang.Long> {
    /**
     * 逻辑删除
     *
     * @param id 主键id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull Long id) {
        Name name = new Name();
        name.setId(id);
        LambdaUpdateWrapper<Name> updateWrapper = Wrappers.lambdaUpdate(name).set(Name::getDeleted, 1);
        return crudDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     * @param name  实体
     * @return  是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Name name) {
        LambdaUpdateWrapper<Name> updateWrapper = Wrappers.lambdaUpdate(name).set(Name::getDeleted, 1);
        // 逻辑删除
        return crudDao.update(updateWrapper);
    }

    /**
     * 批量逻辑删除
     * @param ids 主键集合
     * @return  是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean deleteAllById(@NonNull List<Long> ids) {

        LambdaUpdateWrapper<Name> updateWrapper = Wrappers.<Name>lambdaUpdate()
                .in(Name::getId, ids)
                .set(Name::getDeleted, 1);

        // 逻辑删除
        return crudDao.update(updateWrapper);
    }
}