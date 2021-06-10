package org.arch.ums.account.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.TagRequest;
import org.arch.ums.account.dto.TagSearchDto;
import org.arch.ums.account.entity.Tag;
import org.arch.ums.account.rest.TagRest;
import org.arch.ums.account.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-标签(Tag) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:58
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TagBiz implements CrudBiz<TagRequest, Tag, java.lang.Long, TagSearchDto, TagSearchDto, TagService>, TagRest {

    private final TenantContextHolder tenantContextHolder;
    private final TagService tagService;

    @Override
    public Tag resolver(TokenInfo token, TagRequest request) {
        Tag tag = new Tag();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, tag);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            tag.setTenantId(token.getTenantId());
        }
        else {
            tag.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return tag;
    }

    @Override
    public TagService getCrudService() {
        return tagService;
    }

    @Override
    public TagSearchDto getSearchDto() {
        return new TagSearchDto();
    }

}
