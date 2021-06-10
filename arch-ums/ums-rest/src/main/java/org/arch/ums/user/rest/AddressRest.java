package org.arch.ums.user.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.user.dto.AddressRequest;
import org.arch.ums.user.dto.AddressSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户地址表(Address) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:58:48
 * @since 1.0.0
 */

@RestController
@RequestMapping("/user/address")
public interface AddressRest extends CrudRest<AddressRequest, java.lang.Long, AddressSearchDto> {

}

