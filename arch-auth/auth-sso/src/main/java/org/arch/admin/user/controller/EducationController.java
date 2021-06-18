package org.arch.admin.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.feign.FeignCrudController;
import org.arch.ums.user.dto.EducationRequest;
import org.arch.ums.user.dto.EducationSearchDto;
import org.arch.ums.user.client.UserEducationFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户学历信息(Education) CRUD 控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 19:40:55
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController("adminEducationController")
@RequestMapping("/user/education")
public class EducationController implements FeignCrudController<EducationSearchDto, java.lang.Long, EducationRequest, UserEducationFeignService> {

    private final UserEducationFeignService userEducationService;

    @Override
    public UserEducationFeignService getFeignService() {
        return this.userEducationService;
    }

}
