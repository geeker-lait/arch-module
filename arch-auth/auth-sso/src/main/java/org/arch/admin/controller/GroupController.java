package org.arch.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.FeignCrudController;
import org.arch.ums.account.dto.GroupRequest;
import org.arch.ums.account.entity.Group;
import org.arch.ums.feign.account.client.UmsAccountGroupFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色组 CRUD 控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.24 13:33
 */
@RestController
@RequestMapping("/role")
@Slf4j
@RequiredArgsConstructor
public class GroupController implements FeignCrudController<Group, Long, GroupRequest, UmsAccountGroupFeignService> {

    private final UmsAccountGroupFeignService groupFeignService;

    @Override
    public UmsAccountGroupFeignService getFeignService() {
        return this.groupFeignService;
    }

    @Override
    public Group getEntity() {
        return new Group();
    }

}
