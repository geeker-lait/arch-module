package org.arch.ums.user.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.user.dto.IdCardRequest;
import org.arch.ums.user.dto.IdCardSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户身份证表(IdCard) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:59:23
 * @since 1.0.0
 */

@RestController
@RequestMapping("/user/id/card")
public interface IdCardRest extends CrudRest<IdCardRequest, java.lang.Long, IdCardSearchDto> {

}

