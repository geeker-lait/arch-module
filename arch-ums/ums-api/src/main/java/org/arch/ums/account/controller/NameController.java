package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.NameSearchDto;
import org.arch.ums.account.entity.Name;
import org.arch.ums.account.service.NameService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号名(Name) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:53:57
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/name")
public class NameController implements CrudController<Name, java.lang.Long, NameSearchDto, NameService> {

    private final NameService nameService;

    @Override
    public Name resolver(TokenInfo token, Name name) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 name 后返回 name, 如: tenantId 的处理等.
        return name;
    }

    @Override
    public NameService getCrudService() {
        return nameService;
    }

    @Override
    public NameSearchDto getSearchDto() {
        return new NameSearchDto();
    }

}