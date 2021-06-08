package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.CategoryRequest;
import org.arch.ums.account.dto.CategorySearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-资源类目(Category) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:50:59
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/category")
public interface CategoryRest extends CrudRest<CategoryRequest, java.lang.Long, CategorySearchDto> {

}

