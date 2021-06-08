package org.arch.ums.user.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.user.dto.PhoneRequest;
import org.arch.ums.user.dto.PhoneSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户电话信息(Phone) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:59:34
 * @since 1.0.0
 */

@RestController
@RequestMapping("/user/phone")
public interface PhoneRest extends CrudRest<PhoneRequest, java.lang.Long, PhoneSearchDto> {

}

