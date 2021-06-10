package org.arch.ums.account.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.GroupRequest;
import org.arch.ums.account.dto.GroupSearchDto;
import org.arch.ums.account.entity.Group;
import org.arch.ums.account.rest.GroupRest;
import org.arch.ums.account.service.GroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-组织机构(Group) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:02
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GroupBiz implements CrudBiz<GroupRequest, Group, java.lang.Long, GroupSearchDto, GroupSearchDto, GroupService>, GroupRest {

    private final TenantContextHolder tenantContextHolder;
    private final GroupService groupService;

    @Override
    public Group resolver(TokenInfo token, GroupRequest request) {
        Group group = new Group();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, group);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            group.setTenantId(token.getTenantId());
        }
        else {
            group.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
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
