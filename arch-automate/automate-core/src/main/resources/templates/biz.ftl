package ${package!""};

<#list documents as doc><#if doc.type == "service"><#assign serviceSuffix="${(doc.suffix)?cap_first}" /></#if></#list>
<#list documents as doc><#if doc.type == "searchDto"><#assign searchDtoSuffix="${(doc.suffix)?cap_first}" /></#if></#list>
<#list documents as doc><#if doc.type == "request"><#assign requestSuffix="${(doc.suffix)?cap_first}" /></#if></#list>
<#list documents as doc><#if doc.type == "rest"><#assign restSuffix="${(doc.suffix)?cap_first}" /></#if></#list>

import ${basePkg!""}.${domain!""}.entity.${(name?cap_first)!""}<#list documents as doc><#if doc.type == "entity">${(doc.suffix)?cap_first}</#if></#list>;
import ${basePkg!""}.${domain!""}.dto.${(name?cap_first)!""}<#list documents as doc><#if doc.type == "searchDto">${(doc.suffix)?cap_first}</#if></#list>;
import ${basePkg!""}.${domain!""}.dto.${(name?cap_first)!""}<#list documents as doc><#if doc.type == "request">${(doc.suffix)?cap_first}</#if></#list>;
import ${basePkg!""}.${domain!""}.service.${(name?cap_first)!""}<#list documents as doc><#if doc.type == "service">${(doc.suffix)?cap_first}</#if></#list>;
import ${basePkg!""}.${domain!""}.rest.${(name?cap_first)!""}<#list documents as doc><#if doc.type == "rest">${(doc.suffix)?cap_first}</#if></#list>;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.beans.BeanUtils;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
* @description ${descr!""}
*
* @author ${author!""}
* @date ${.now}
*/
@Slf4j
@RequiredArgsConstructor
public class ${(name?cap_first)!""}${suffix!""} implements CrudBiz<${(name?cap_first)!""}${requestSuffix!""}, ${(name?cap_first)!""}, Long, ${(name?cap_first)!""}${searchDtoSuffix!""}, ${(name?cap_first)!""}${searchDtoSuffix!""}, ${(name?cap_first)!""}${serviceSuffix!""}Service>, ${(name?cap_first)!""}${restSuffix!""} {

    private final TenantContextHolder tenantContextHolder;
    private final ${(name?cap_first)!""}${serviceSuffix!""} ${(name?uncap_first)!""}${serviceSuffix!""};

    @Override
    public ${(name?cap_first)!""}${suffix!""} resolver(TokenInfo token, ${(name?cap_first)!""}${requestSuffix!""} request) {
        ${(name?cap_first)!""}${suffix!""} ${(name?uncap_first)!""} = new ${(name?cap_first)!""}${suffix!""}();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, ${(name?uncap_first)!""});
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
    public ${(name?cap_first)!""}${serviceSuffix!""} getCrudService() {
        return ${(name?uncap_first)!""}${serviceSuffix!""};
    }

    @Override
    public ${(name?cap_first)!""}${searchDtoSuffix!""} getSearchDto() {
        return new ${(name?uncap_first)!""}${searchDtoSuffix!""}();
    }
}
