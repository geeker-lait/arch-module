package org.arch.ums.user.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.user.dto.RelativesRequest;
import org.arch.ums.user.dto.RelativesSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户亲朋信息(Relatives) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:59:38
 * @since 1.0.0
 */

@RestController
@RequestMapping("/user/relatives")
public interface RelativesRest extends CrudRest<RelativesRequest, java.lang.Long, RelativesSearchDto> {

}

