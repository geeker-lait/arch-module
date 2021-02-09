package org.arch.application.demo.ums.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.application.demo.ums.dto.IdentifierSearchDto;
import org.arch.application.demo.ums.entity.Identifier;
import org.arch.application.demo.ums.service.IdentifierService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户-标识(Identifier) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:52:34
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/identifier")
public class IdentifierController implements CrudController<Identifier, java.lang.Long, IdentifierSearchDto, IdentifierService> {

    private final IdentifierService identifierService;

    @Override
    public Identifier resolver(TokenInfo token, Identifier identifier) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 identifier 后返回 identifier, 如: tenantId 的处理等.
        return identifier;
    }

    @Override
    public IdentifierService getCrudService() {
        return identifierService;
    }

    @Override
    public IdentifierSearchDto getSearchDto() {
        return new IdentifierSearchDto();
    }

}
