package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.Category;
import org.arch.ums.account.mapper.CategoryMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-资源类目(Category) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:49:06
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class CategoryDao extends CrudServiceImpl<CategoryMapper, Category> implements CrudDao<Category> {
    private final CategoryMapper categoryMapper;
}