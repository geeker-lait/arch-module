package org.arch.ums.user.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.user.dao.RelativesDao;
import org.arch.ums.user.entity.Relatives;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户亲朋信息(Relatives) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:31:57
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RelativesService extends CrudService<Relatives, java.lang.Long> {
    private final RelativesDao relativesDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Relatives entity = new Relatives();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Relatives> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Relatives::getDeleted, 1);
        return relativesDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Relatives entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Relatives> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Relatives::getDeleted, 1);
        // 逻辑删除
        return relativesDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Relatives> updateWrapper = Wrappers.<Relatives>lambdaUpdate()
                .eq(Relatives::getDeleted, 0)
                .in(Relatives::getId, ids)
                .set(Relatives::getDeleted, 1);

        // 逻辑删除
        return relativesDao.update(updateWrapper);
    }
}
