package ${package!""};
<#if pk??>
    <#if columns?? && (columns?size > 0)>
        <#list columns as column>
            <#if column!"" == pk>
                // TODO 不能获取数据库类型对应的 JavaType
                <#assign _pkType = column.typ>
            </#if>
        </#list>
    </#if>
</#if>
<#if _pkType??>
<#else>
    <#assign _pkType = "java.lang.Long">
</#if>

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ${basePkg!""}.dto.${(name?cap_first)!""}SearchDto;
import ${basePkg!""}.entity.${(name?cap_first)!""};
import ${basePkg!""}.service.${(name?cap_first)!""}Service;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.beans.Response;
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
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;
<#if _pkType??>
import ${_pkType}
</#if>

/**
 * ${comment!""}(${(name?cap_first)!""}) 表服务控制器
 *
 * @author $!author
 * @date   $!time.currTime()
 * @since  1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/${(name?uncap_first)!""}")
public class ${(name?cap_first)!""}${suffix!""} implements CrudController<${(name?cap_first)!""}, ${_pkType!""}, ${
(name?cap_first)!""}SearchDto, ${(name?cap_first)!""}Service> {

    private final TenantContextHolder tenantContextHolder;
    private final ${(name?cap_first)!""}${suffix!""} ${(name?uncap_first)!""}${suffix!""};

    @Override
    public ${(name?cap_first)!""} resolver(TokenInfo token, ${(name?cap_first)!""} ${(name?uncap_first)!""}) {
        if (isNull(${(name?uncap_first)!""}) {
            ${(name?uncap_first)!""} =  new ${(name?cap_first)!""}();
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            ${(name?uncap_first)!""}.setTenantId(token.getTenantId());
        }
        else {
            ${(name?uncap_first)!""}.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return ${(name?uncap_first)!""};
    }

    @Override
    public ${(name?cap_first)!""}${suffix!""} getCrudService() {
        return ${(name?uncap_first)!""}${suffix!""};
    }

    @Override
    public ${(name?cap_first)!""}SearchDto getSearchDto() {
        return new ${(name?uncap_first)!""}SearchDto();
    }

    /**
    * 根据 entity 条件查询对象.
    * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
    * @param entity    实体类
    * @param token     token info
    * @return  {@link Response}
    */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<${(name?cap_first)!""}> findOne(@RequestBody ${(name?cap_first)!""} entity, TokenInfo token) {
        try {
            resolver(token, entity);
            ${(name?cap_first)!""}SearchDto searchDto = convertSearchDto(entity);
            ${(name?cap_first)!""} t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(t);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(),"查询到多个结果");
            } else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
    * 根据 entity 条件查询对象列表.
    * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
    * @param t         实体类
    * @param token     token info
    * @return  {@link Response}
    */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<${(name?cap_first)!""}>> find(@RequestBody ${(name?cap_first)!""} t, TokenInfo token) {
        resolver(token, t);
        ${(name?cap_first)!""}SearchDto searchDto = convertSearchDto(t);
        try {
            return Response.success(getCrudService().findAllByMapParams(searchDto.getSearchParams()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
    * 分页查询.
    * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
    * @param entity        实体类
    * @param pageNumber    第几页
    * @param pageSize      页大小
    * @param token         token info
    * @return  {@link Response}
    */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<${(name?cap_first)!""}>> page(@RequestBody ${(name?cap_first)!""} entity,
                                                    @PathVariable(value = "pageNumber") Integer pageNumber,
                                                    @PathVariable(value = "pageSize") Integer pageSize,
                                                    TokenInfo token) {
        resolver(token, entity);
        ${(name?cap_first)!""}SearchDto searchDto = convertSearchDto(entity);
        try {
            return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
