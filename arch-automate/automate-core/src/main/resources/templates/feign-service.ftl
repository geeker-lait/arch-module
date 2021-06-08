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

import org.arch.framework.web.feign.FeignApi;
import ${basePkg!""}.dto.${(name?cap_first)!""}SearchDto;
import ${basePkg!""}.dto.${(name?cap_first)!""}Request;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;


/**
 * ${comment!""}(${(name?cap_first)!""})服务远程调用的 feign 客户端.
 * TODO 增加对列(xx_xx)转换为驼峰类型字段,
 * @author ${author!""}
 * @date ${.now}
 * @since  1.0.0
 */
@Component
@FeignClient(name = "arch-${basePkg?keep_after_last(".")}-api", contextId = "arch-${basePkg?keep_after_last(".")}-api-${(name?cap_first)!""}", path="<#if basePkg??>/${basePkg?keep_after_last(".")}</#if>/${(name?uncap_first)!""}",
configuration = DeFaultFeignConfig.class)
public interface ${(name?cap_first)!""}${suffix!""} extends BaseFeignService<${(name?cap_first)!""}SearchDto, ${(name?cap_first)!""}Request, ${_pkType!"java.lang.Long"}> {


}