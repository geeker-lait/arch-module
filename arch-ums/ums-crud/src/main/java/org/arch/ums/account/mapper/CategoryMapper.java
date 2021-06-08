package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.Category;

/**
 * 账号-资源类目(Category) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:49:35
 * @since 1.0.0
 */
@Mapper
public interface CategoryMapper extends CrudMapper<Category> {

}