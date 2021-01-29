package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.GroupSearchDto;
import org.arch.ums.account.entity.Group;
import org.arch.ums.account.service.GroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-组织机构(Group) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:01:24
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/group")
public class GroupController implements CrudController<Group, java.lang.Long, GroupSearchDto, GroupService> {

    private final GroupService groupService;

    @Override
    public Group resolver(TokenInfo token, Group group) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 group 后返回 group, 如: tenantId 的处理等.
        return group;
    }

    @Override
    public GroupService getCrudService() {
        return groupService;
    }

    @Override
    public GroupSearchDto getSearchDto() {
        return new GroupSearchDto();
    }

}