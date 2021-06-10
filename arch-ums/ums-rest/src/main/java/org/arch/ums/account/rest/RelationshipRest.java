package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.RelationshipRequest;
import org.arch.ums.account.dto.RelationshipSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-关系(Relationship) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:51:50
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/relationship")
public interface RelationshipRest extends CrudRest<RelationshipRequest, java.lang.Long, RelationshipSearchDto> {

}

