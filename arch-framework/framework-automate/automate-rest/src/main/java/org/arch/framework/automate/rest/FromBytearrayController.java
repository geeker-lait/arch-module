package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.arch.framework.automate.api.dto.FromBytearraySearchDto;
import org.arch.framework.automate.from.entity.FromBytearray;
import org.arch.framework.automate.from.service.FromBytearrayService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

/**
 * 表单数据(FromBytearray) 表服务控制器
 *
 * @author lait
 * @date 2021-02-08 13:25:30
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/from/bytearray")
public class FromBytearrayController implements CrudController<FromBytearray, java.lang.Long, FromBytearraySearchDto, FromBytearrayService> {

    private final AppProperties appProperties;
    private final FromBytearrayService fromBytearrayService;

    @Override
    public FromBytearray resolver(TokenInfo token, FromBytearray fromBytearray) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 fromBytearray 后返回 fromBytearray, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            fromBytearray.setTenantId(token.getTenantId());
        } else {
            fromBytearray.setTenantId(appProperties.getSystemTenantId());
        }
        return fromBytearray;
    }

    @Override
    public FromBytearrayService getCrudService() {
        return fromBytearrayService;
    }

    @Override
    public FromBytearraySearchDto getSearchDto() {
        return new FromBytearraySearchDto();
    }

}
