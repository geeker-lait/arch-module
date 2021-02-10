package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.TagSearchDto;
import org.arch.ums.account.entity.Tag;
import org.arch.ums.account.service.TagService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 账号-标签(Tag) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:41:44
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/tag")
public class TagController implements CrudController<Tag, Long, TagSearchDto, TagService> {

    private final TenantContextHolder tenantContextHolder;
    private final TagService tagService;

    @Override
    public Tag resolver(TokenInfo token, Tag tag) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 tag 后返回 tag, 如: tenantId 的处理等.
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