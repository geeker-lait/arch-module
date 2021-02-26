package org.arch.ums.user.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.arch.ums.user.entity.Education;
import org.arch.ums.user.dao.EducationDao;
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
 * 用户学历信息(Education) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:31:58
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EducationService extends CrudService<Education, java.lang.Long> {
    private final EducationDao educationDao = (EducationDao) crudDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Education entity = new Education();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Education> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Education::getDeleted, 1);
        return educationDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Education entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Education> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Education::getDeleted, 1);
        // 逻辑删除
        return educationDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Education> updateWrapper = Wrappers.<Education>lambdaUpdate()
                .eq(Education::getDeleted, 0)
                .and(w -> w.in(Education::getId, ids))
                .set(Education::getDeleted, 1);

        // 逻辑删除
        return educationDao.update(updateWrapper);
    }
}
