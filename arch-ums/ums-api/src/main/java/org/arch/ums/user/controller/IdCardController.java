package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.IdCardSearchDto;
import org.arch.ums.user.entity.IdCard;
import org.arch.ums.user.service.IdCardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户身份证表(IdCard) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:06:40
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/id/card")
public class IdCardController implements CrudController<IdCard, java.lang.Long, IdCardSearchDto, IdCardService> {

    private final IdCardService idCardService;

    @Override
    public IdCard resolver(TokenInfo token, IdCard idCard) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 idCard 后返回 idCard, 如: tenantId 的处理等.
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