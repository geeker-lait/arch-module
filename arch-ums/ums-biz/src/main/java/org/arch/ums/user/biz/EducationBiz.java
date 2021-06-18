package org.arch.ums.user.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.EducationRequest;
import org.arch.ums.user.dto.EducationSearchDto;
import org.arch.ums.user.entity.Education;
import org.arch.ums.user.service.EducationService;
import org.arch.ums.user.rest.EducationRest;
import org.springframework.beans.BeanUtils;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 用户学历信息(Education) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:53:15
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class EducationBiz implements CrudBiz<EducationRequest, Education, java.lang.Long, EducationSearchDto, EducationSearchDto, EducationService>, EducationRest {

    private final TenantContextHolder tenantContextHolder;
    private final EducationService educationService;

    @Override
    public Education resolver(TokenInfo token, EducationRequest request) {
        Education education = new Education();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, education);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            education.setTenantId(token.getTenantId());
        }
        else {
            education.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return education;
    }

    @Override
    public EducationService getCrudService() {
        return educationService;
    }

    @Override
    public EducationSearchDto getSearchDto() {
        return new EducationSearchDto();
    }

}
