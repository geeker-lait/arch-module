package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.PostRequest;
import org.arch.ums.account.dto.PostSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-岗位(Post) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:51:45
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/post")
public interface PostRest extends CrudRest<PostRequest, java.lang.Long, PostSearchDto> {

}

