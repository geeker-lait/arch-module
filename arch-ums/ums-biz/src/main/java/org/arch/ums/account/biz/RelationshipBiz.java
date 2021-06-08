package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dao.RelationshipDao;
import org.arch.ums.account.dto.RelationshipRequest;
import org.arch.ums.account.dto.RelationshipSearchDto;
import org.arch.ums.account.entity.Relationship;
import org.arch.ums.account.rest.RelationshipRest;
import org.arch.ums.account.service.RelationshipService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 账号-关系(Relationship) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RelationshipBiz implements CrudBiz<RelationshipRequest, Relationship, java.lang.Long, RelationshipSearchDto, RelationshipSearchDto, RelationshipService>, RelationshipRest {

    private final TenantContextHolder tenantContextHolder;
    private final RelationshipService relationshipService;
    private final RelationshipDao relationshipDao;

    @Override
    public Relationship resolver(TokenInfo token, RelationshipRequest request) {
        Relationship relationship = new Relationship();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, relationship);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            relationship.setTenantId(token.getTenantId());
        }
        else {
            relationship.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return relationship;
    }

    @Override
    public RelationshipService getCrudService() {
        return relationshipService;
    }

    @Override
    public RelationshipSearchDto getSearchDto() {
        return new RelationshipSearchDto();
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
    public RelationshipSearchDto findOne(RelationshipRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Relationship relationship = resolver(token, request);
        RelationshipSearchDto searchDto = convertSearchDto(relationship);
        Relationship result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<RelationshipSearchDto> find(RelationshipRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Relationship relationship = resolver(token, request);
        RelationshipSearchDto searchDto = convertSearchDto(relationship);
        List<Relationship> relationshipList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return relationshipList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<RelationshipSearchDto> page(RelationshipRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Relationship relationship = resolver(token, request);
        RelationshipSearchDto searchDto = convertSearchDto(relationship);
        IPage<Relationship> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

    /**
     * 保存, 如果 seq 或 org 等于 null, 则通过 sql max(org/seq) + 1 自增
     * @param request      实体的 request 封装类型
     * @return  {@link RelationshipSearchDto}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RelationshipSearchDto save(RelationshipRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Relationship relationship = resolver(token, request);
        boolean isSuccess = false;
        if (isNull(request.getSeq()) || isNull(request.getOrg())) {
            isSuccess = saveMax(relationship);
        }
        else {
            isSuccess = relationshipService.save(relationship);
        }
        if (!isSuccess) {
            throw new BusinessException("保存失败");
        }
        return convertReturnDto(relationship);
    }

    /**
     * 保存, 如果 seq 或 org 等于 null, 则通过 sql max(org/seq) + 1 自增
     * @param relationship  {@link Relationship}
     * @return 是否保存成功
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean saveMax(@NonNull Relationship relationship) {
        return relationshipDao.saveMax(relationship);
    }

}
