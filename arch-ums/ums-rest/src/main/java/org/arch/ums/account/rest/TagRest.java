package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.TagRequest;
import org.arch.ums.account.dto.TagSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-标签(Tag) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:52:30
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/tag")
public interface TagRest extends CrudRest<TagRequest, java.lang.Long, TagSearchDto> {

}

