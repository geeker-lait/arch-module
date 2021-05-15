package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dto.TicketRequest;
import org.arch.ums.account.dto.TicketSearchDto;
import org.arch.ums.account.entity.Ticket;
import org.arch.ums.account.service.TicketService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.beans.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 账号-券(Ticket) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:14:54
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/ticket")
public class TicketController implements CrudController<TicketRequest, Ticket, java.lang.Long, TicketSearchDto, TicketService> {

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
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<TicketSearchDto> findOne(@RequestBody @Valid TicketRequest request, TokenInfo token) {
        try {
            Ticket ticket = resolver(token, request);
            TicketSearchDto searchDto = convertSearchDto(ticket);
            Ticket result = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(convertSearchDto(result));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(), "查询到多个结果");
            }
            else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<TicketSearchDto>> find(@RequestBody @Valid TicketRequest request, TokenInfo token) {
        Ticket ticket = resolver(token, request);
        TicketSearchDto searchDto = convertSearchDto(ticket);
        try {
            List<Ticket> ticketList = getCrudService().findAllByMapParams(searchDto.getSearchParams());
            return Response.success(ticketList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<TicketSearchDto>> page(@RequestBody @Valid TicketRequest request,
                                                 @PathVariable(value = "pageNumber") Integer pageNumber,
                                                 @PathVariable(value = "pageSize") Integer pageSize,
                                                 TokenInfo token) {
        Ticket ticket = resolver(token, request);
        TicketSearchDto searchDto = convertSearchDto(ticket);
        try {
            IPage<Ticket> page = getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
