package org.arch.ums.user.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.user.dto.BankCardRequest;
import org.arch.ums.user.dto.BankCardSearchDto;
import org.arch.ums.user.entity.BankCard;
import org.arch.ums.user.service.BankCardService;
import org.arch.ums.user.rest.BankCardRest;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 用户银行卡信息(BankCard) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:53:15
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class BankCardBiz implements CrudBiz<BankCardRequest, BankCard, java.lang.Long, BankCardSearchDto, BankCardSearchDto, BankCardService>, BankCardRest {

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
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public BankCardSearchDto findOne(BankCardRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        BankCard bankCard = resolver(token, request);
        BankCardSearchDto searchDto = convertSearchDto(bankCard);
        BankCard result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<BankCardSearchDto> find(BankCardRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        BankCard bankCard = resolver(token, request);
        BankCardSearchDto searchDto = convertSearchDto(bankCard);
        List<BankCard> bankCardList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return bankCardList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<BankCardSearchDto> page(BankCardRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        BankCard bankCard = resolver(token, request);
        BankCardSearchDto searchDto = convertSearchDto(bankCard);
        IPage<BankCard> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
