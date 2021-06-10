package org.arch.ums.account.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.TicketRequest;
import org.arch.ums.account.dto.TicketSearchDto;
import org.arch.ums.account.entity.Ticket;
import org.arch.ums.account.rest.TicketRest;
import org.arch.ums.account.service.TicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-券(Ticket) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:59
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TicketBiz implements CrudBiz<TicketRequest, Ticket, java.lang.Long, TicketSearchDto, TicketSearchDto, TicketService>, TicketRest {

    private final TenantContextHolder tenantContextHolder;
    private final TicketService ticketService;

    @Override
    public Ticket resolver(TokenInfo token, TicketRequest request) {
        Ticket ticket = new Ticket();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, ticket);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            ticket.setTenantId(token.getTenantId());
        }
        else {
            ticket.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
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
