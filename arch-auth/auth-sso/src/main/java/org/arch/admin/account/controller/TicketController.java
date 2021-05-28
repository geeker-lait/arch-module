package org.arch.admin.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudController;
import org.arch.ums.account.client.AccountTicketFeignService;
import org.arch.ums.account.dto.TicketRequest;
import org.arch.ums.account.dto.TicketSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 账号-券(Ticket) CRUD 控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 19:34:44
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController("adminTicketController")
@RequestMapping("/account/ticket")
public class TicketController implements FeignCrudController<TicketSearchDto, java.lang.Long, TicketRequest, AccountTicketFeignService> {

    private final AccountTicketFeignService accountTicketService;

    @Override
    public AccountTicketFeignService getFeignService() {
        return this.accountTicketService;
    }

}
