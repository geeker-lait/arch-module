package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.entity.Ticket;
import org.springframework.stereotype.Service;

/**
 * 账号-券(Ticket) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:30:57
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService extends CrudService<Ticket, java.lang.Long> {

}