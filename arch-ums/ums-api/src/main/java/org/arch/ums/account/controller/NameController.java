package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.NameSearchDto;
import org.arch.ums.account.entity.Name;
import org.arch.ums.account.service.NameService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 账号名(Name) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:38:35
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/name")
public class NameController implements CrudController<Name, Long, NameSearchDto, NameService> {

    private final TenantContextHolder tenantContextHolder;
    private final NameService nameService;

    @Override
    public Name resolver(TokenInfo token, Name name) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            name.setTenantId(token.getTenantId());
        }
        else {
            name.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
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