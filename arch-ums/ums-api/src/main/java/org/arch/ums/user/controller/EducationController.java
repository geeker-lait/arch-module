package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.EducationSearchDto;
import org.arch.ums.user.entity.Education;
import org.arch.ums.user.service.EducationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户学历信息(Education) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:04:31
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/education")
public class EducationController implements CrudController<Education, java.lang.Long, EducationSearchDto, EducationService> {

    private final EducationService educationService;

    @Override
    public Education resolver(TokenInfo token, Education education) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 education 后返回 education, 如: tenantId 的处理等.
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