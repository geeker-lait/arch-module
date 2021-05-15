package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.user.dto.BankCardRequest;
import org.arch.ums.user.dto.BankCardSearchDto;
import org.arch.ums.user.entity.BankCard;
import org.arch.ums.user.service.BankCardService;
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
 * 用户银行卡信息(BankCard) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 23:08:41
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/bank/card")
public class BankCardController implements CrudController<BankCardRequest, BankCard, java.lang.Long, BankCardSearchDto, BankCardService> {

    private final TenantContextHolder tenantContextHolder;
    private final BankCardService bankCardService;

    @Override
    public BankCard resolver(TokenInfo token, BankCardRequest request) {
        BankCard bankCard = new BankCard();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, bankCard);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            bankCard.setTenantId(token.getTenantId());
        }
        else {
            bankCard.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return bankCard;
    }

    @Override
    public BankCardService getCrudService() {
        return bankCardService;
    }

    @Override
    public BankCardSearchDto getSearchDto() {
        return new BankCardSearchDto();
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
    public Response<BankCardSearchDto> findOne(@RequestBody @Valid BankCardRequest request, TokenInfo token) {
        try {
            BankCard bankCard = resolver(token, request);
            BankCardSearchDto searchDto = convertSearchDto(bankCard);
            BankCard result = getCrudService().findOneByMapParams(searchDto.getSearchParams());
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
    public Response<List<BankCardSearchDto>> find(@RequestBody @Valid BankCardRequest request, TokenInfo token) {
        BankCard bankCard = resolver(token, request);
        BankCardSearchDto searchDto = convertSearchDto(bankCard);
        try {
            List<BankCard> bankCardList = getCrudService().findAllByMapParams(searchDto.getSearchParams());
            return Response.success(bankCardList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<BankCardSearchDto>> page(@RequestBody @Valid BankCardRequest request,
                                                   @PathVariable(value = "pageNumber") Integer pageNumber,
                                                   @PathVariable(value = "pageSize") Integer pageSize,
                                                   TokenInfo token) {
        BankCard bankCard = resolver(token, request);
        BankCardSearchDto searchDto = convertSearchDto(bankCard);
        try {
            IPage<BankCard> page = getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
