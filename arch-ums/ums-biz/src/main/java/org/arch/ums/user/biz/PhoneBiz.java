package org.arch.ums.user.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.PhoneRequest;
import org.arch.ums.user.dto.PhoneSearchDto;
import org.arch.ums.user.entity.Phone;
import org.arch.ums.user.rest.PhoneRest;
import org.arch.ums.user.service.PhoneService;
import org.springframework.beans.BeanUtils;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 用户电话信息(Phone) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:53:16
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class PhoneBiz implements CrudBiz<PhoneRequest, Phone, java.lang.Long, PhoneSearchDto, PhoneSearchDto, PhoneService>, PhoneRest {

    private final TenantContextHolder tenantContextHolder;
    private final PhoneService phoneService;

    @Override
    public Phone resolver(TokenInfo token, PhoneRequest request) {
        Phone phone = new Phone();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, phone);
        }
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
