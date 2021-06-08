package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.MemberRequest;
import org.arch.ums.account.dto.MemberSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-会员账号(Member) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:51:15
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/member")
public interface MemberRest extends CrudRest<MemberRequest, java.lang.Long, MemberSearchDto> {

}

