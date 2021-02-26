package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.CategoryDao;
import org.arch.ums.account.entity.Category;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号-资源类目(Category) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:07:22
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService extends CrudService<Category, java.lang.Long> {
    private final CategoryDao categoryDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Category entity = new Category();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Category> updateWrapper = Wrappers.<Category>lambdaUpdate(entity)
                                                              .set(Category::getDeleted, 1);
        return categoryDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Category entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Category> updateWrapper = Wrappers.<Category>lambdaUpdate(entity)
                .set(Category::getDeleted, 1);
        // 逻辑删除
        return categoryDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Category> updateWrapper = Wrappers.<Category>lambdaUpdate()
                .eq(Category::getDeleted, 0)
                .and(w -> w.in(Category::getId, ids))
                .set(Category::getDeleted, 1);

        // 逻辑删除
        return categoryDao.update(updateWrapper);
    }
}
