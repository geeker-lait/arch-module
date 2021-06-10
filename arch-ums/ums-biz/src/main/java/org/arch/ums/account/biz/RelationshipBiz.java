package org.arch.ums.account.biz;

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
     * 保存, 如果 seq 或 org 等于 null, 则通过 sql max(org/seq) + 1 自增
     * @param request      实体的 request 封装类型
     * @return  {@link RelationshipSearchDto}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RelationshipSearchDto save(RelationshipRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
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
