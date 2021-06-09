package org.arch.admin.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.account.api.AccountTicketApi;
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
public class TicketApi implements FeignCrudApi<TicketSearchDto, Long, TicketRequest, AccountTicketApi> {

    private final AccountTicketApi accountTicketApi;

    @Override
    public AccountTicketApi getApi() {
        return this.accountTicketApi;
    }

}
