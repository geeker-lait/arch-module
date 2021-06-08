package org.arch.admin.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.user.api.UserEducationApi;
import org.arch.ums.user.dto.EducationRequest;
import org.arch.ums.user.dto.EducationSearchDto;
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
public class EducationApi implements FeignCrudApi<EducationSearchDto, Long, EducationRequest, UserEducationApi> {

    private final UserEducationApi userEducationApi;

    @Override
    public UserEducationApi getFeignApi() {
        return this.userEducationApi;
    }

}
