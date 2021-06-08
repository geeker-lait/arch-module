package org.arch.ums.member.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.member.dto.MemberRightsRequest;
import org.arch.ums.member.dto.MemberRightsSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员权益(MemberRights) rest 层
 *
 * @author zheng
 * @date 2021-06-06 12:01:21
 * @since 1.0.0
 */

@RestController
@RequestMapping("/member/rights")
public interface MemberRightsRest extends CrudRest<MemberRightsRequest, java.lang.Long, MemberRightsSearchDto> {

}

