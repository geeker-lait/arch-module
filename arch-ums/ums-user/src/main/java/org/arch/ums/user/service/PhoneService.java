package org.arch.ums.user.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.user.dao.PhoneDao;
import org.arch.ums.user.entity.Phone;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户电话信息(Phone) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:31:58
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PhoneService extends CrudService<Phone, java.lang.Long> {
    private final PhoneDao phoneDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Phone entity = new Phone();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Phone> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Phone::getDeleted, 1);
        return phoneDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Phone entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Phone> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Phone::getDeleted, 1);
        // 逻辑删除
        return phoneDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Phone> updateWrapper = Wrappers.<Phone>lambdaUpdate()
                .eq(Phone::getDeleted, 0)
                .and(w -> w.in(Phone::getId, ids))
                .set(Phone::getDeleted, 1);

        // 逻辑删除
        return phoneDao.update(updateWrapper);
    }
}
