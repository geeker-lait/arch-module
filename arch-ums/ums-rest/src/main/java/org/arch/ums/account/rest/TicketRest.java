package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.TicketRequest;
import org.arch.ums.account.dto.TicketSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号-券(Ticket) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:52:36
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/ticket")
public interface TicketRest extends CrudRest<TicketRequest, java.lang.Long, TicketSearchDto> {

}

