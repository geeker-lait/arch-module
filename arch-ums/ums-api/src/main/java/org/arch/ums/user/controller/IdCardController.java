package org.arch.ums.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.IdCardSearchDto;
import org.arch.ums.user.entity.IdCard;
import org.arch.ums.user.service.IdCardService;
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
 * 用户身份证表(IdCard) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-26 23:19:52
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/id/card")
public class IdCardController implements CrudController<IdCard, java.lang.Long, IdCardSearchDto, IdCardService> {

    private final TenantContextHolder tenantContextHolder;
    private final IdCardService idCardService;

    @Override
    public IdCard resolver(TokenInfo token, IdCard idCard) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            idCard.setTenantId(token.getTenantId());
        }
        else {
            idCard.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return idCard;
    }

    @Override
    public IdCardService getCrudService() {
        return idCardService;
    }

    @Override
    public IdCardSearchDto getSearchDto() {
        return new IdCardSearchDto();
    }

}
