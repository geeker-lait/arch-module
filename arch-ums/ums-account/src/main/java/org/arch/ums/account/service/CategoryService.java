package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.entity.Category;
import org.springframework.stereotype.Service;

/**
 * 账号-资源类目(Category) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:49:19
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService extends CrudService<Category, java.lang.Long> {

}