package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.GroupRequest;
import org.arch.ums.account.dto.GroupSearchDto;
import org.arch.ums.account.entity.Group;
import org.arch.ums.account.rest.GroupRest;
import org.arch.ums.account.service.GroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 账号-组织机构(Group) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:02
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GroupBiz implements CrudBiz<GroupRequest, Group, java.lang.Long, GroupSearchDto, GroupSearchDto, GroupService>, GroupRest {

    private final TenantContextHolder tenantContextHolder;
    private final GroupService groupService;

    @Override
    public Group resolver(TokenInfo token, GroupRequest request) {
        Group group = new Group();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, group);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            group.setTenantId(token.getTenantId());
        }
        else {
            group.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return group;
    }

    @Override
    public GroupService getCrudService() {
        return groupService;
    }

    @Override
    public GroupSearchDto getSearchDto() {
        return new GroupSearchDto();
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
    public GroupSearchDto findOne(GroupRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Group group = resolver(token, request);
        GroupSearchDto searchDto = convertSearchDto(group);
        Group result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<GroupSearchDto> find(GroupRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Group group = resolver(token, request);
        GroupSearchDto searchDto = convertSearchDto(group);
        List<Group> groupList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return groupList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<GroupSearchDto> page(GroupRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Group group = resolver(token, request);
        GroupSearchDto searchDto = convertSearchDto(group);
        IPage<Group> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
