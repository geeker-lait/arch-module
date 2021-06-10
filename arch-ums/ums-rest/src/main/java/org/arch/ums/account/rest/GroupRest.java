package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.GroupRequest;
import org.arch.ums.account.dto.GroupSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-组织机构(Group) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:51:04
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/group")
public interface GroupRest extends CrudRest<GroupRequest, java.lang.Long, GroupSearchDto> {

}

