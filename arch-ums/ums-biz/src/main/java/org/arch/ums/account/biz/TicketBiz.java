package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.TicketRequest;
import org.arch.ums.account.dto.TicketSearchDto;
import org.arch.ums.account.entity.Ticket;
import org.arch.ums.account.rest.TicketRest;
import org.arch.ums.account.service.TicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public TicketSearchDto findOne(TicketRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Ticket ticket = resolver(token, request);
        TicketSearchDto searchDto = convertSearchDto(ticket);
        Ticket result = getCrudService().findOneByMapParams(searchDto.searchParams());
        return convertReturnDto(result);
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO List
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<TicketSearchDto> find(TicketRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Ticket ticket = resolver(token, request);
        TicketSearchDto searchDto = convertSearchDto(ticket);
        List<Ticket> ticketList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return ticketList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @return {@link IPage}
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public IPage<TicketSearchDto> page(TicketRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        Ticket ticket = resolver(token, request);
        TicketSearchDto searchDto = convertSearchDto(ticket);
        IPage<Ticket> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
