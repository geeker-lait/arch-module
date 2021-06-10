package org.arch.ums.user.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.user.dto.EducationRequest;
import org.arch.ums.user.dto.EducationSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户学历信息(Education) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:59:17
 * @since 1.0.0
 */

@RestController
@RequestMapping("/user/education")
public interface EducationRest extends CrudRest<EducationRequest, java.lang.Long, EducationSearchDto> {

}

