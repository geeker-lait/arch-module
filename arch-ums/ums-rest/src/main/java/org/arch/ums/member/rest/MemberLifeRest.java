package org.arch.ums.member.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.member.dto.MemberLifeRequest;
import org.arch.ums.member.dto.MemberLifeSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员生命周期(MemberLife) rest 层
 *
 * @author zheng
 * @date 2021-06-06 12:01:05
 * @since 1.0.0
 */

@RestController
@RequestMapping("/member/life")
public interface MemberLifeRest extends CrudRest<MemberLifeRequest, java.lang.Long, MemberLifeSearchDto> {

}

