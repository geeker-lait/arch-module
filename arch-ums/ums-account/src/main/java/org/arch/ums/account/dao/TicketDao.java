package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.Ticket;
import org.arch.ums.account.mapper.TicketMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-券(Ticket) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:30:53
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class TicketDao extends CrudServiceImpl<TicketMapper, Ticket> implements CrudDao<Ticket> {
    private final TicketMapper ticketMapper;
}