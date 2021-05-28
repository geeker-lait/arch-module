package org.arch.admin.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudController;
import org.arch.ums.account.client.AccountOperateLogFeignService;
import org.arch.ums.account.dto.OperateLogRequest;
import org.arch.ums.account.dto.OperateLogSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 账号操作记录(OperateLog) CRUD 控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 19:36:11
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController("adminOperateLogController")
@RequestMapping("/account/operate/log")
public class OperateLogController implements FeignCrudController<OperateLogSearchDto, java.lang.Long, OperateLogRequest, AccountOperateLogFeignService> {

    private final AccountOperateLogFeignService accountOperateLogService;

    @Override
    public AccountOperateLogFeignService getFeignService() {
        return this.accountOperateLogService;
    }

}
