package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.TicketSearchDto;
import org.arch.ums.account.entity.Ticket;
import org.arch.ums.account.service.TicketService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-券(Ticket) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:30:57
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/ticket")
public class TicketController implements CrudController<Ticket, java.lang.Long, TicketSearchDto, TicketService> {

    private final TicketService ticketService;

    @Override
    public Ticket resolver(TokenInfo token, Ticket ticket) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 ticket 后返回 ticket, 如: tenantId 的处理等.
        return ticket;
    }

    @Override
    public TicketService getCrudService() {
        return ticketService;
    }

    @Override
    public TicketSearchDto getSearchDto() {
        return new TicketSearchDto();
    }

}