package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.NameRequest;
import org.arch.ums.account.dto.NameSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号名(Name) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:51:26
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/name")
public interface NameRest extends CrudRest<NameRequest, java.lang.Long, NameSearchDto> {

}

