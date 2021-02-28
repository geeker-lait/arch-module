package org.arch.ums.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.PhoneSearchDto;
import org.arch.ums.user.entity.Phone;
import org.arch.ums.user.service.PhoneService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
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
 * 用户电话信息(Phone) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-26 23:19:52
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/phone")
public class PhoneController implements CrudController<Phone, java.lang.Long, PhoneSearchDto, PhoneService> {

    private final TenantContextHolder tenantContextHolder;
    private final PhoneService phoneService;

    @Override
    public Phone resolver(TokenInfo token, Phone phone) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            phone.setTenantId(token.getTenantId());
        }
        else {
            phone.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return phone;
    }

    @Override
    public PhoneService getCrudService() {
        return phoneService;
    }

    @Override
    public PhoneSearchDto getSearchDto() {
        return new PhoneSearchDto();
    }

}
