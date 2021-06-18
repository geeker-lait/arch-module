package org.arch.ums.member.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.member.dto.MemberLevelRequest;
import org.arch.ums.member.dto.MemberLevelSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员级别(MemberLevel) rest 层
 *
 * @author zheng
 * @date 2021-06-06 12:00:47
 * @since 1.0.0
 */

@RestController
@RequestMapping("/member/level")
public interface MemberLevelRest extends CrudRest<MemberLevelRequest, java.lang.Long, MemberLevelSearchDto> {

}

