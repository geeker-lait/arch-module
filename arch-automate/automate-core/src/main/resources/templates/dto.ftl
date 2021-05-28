package ${package!""};

import org.arch.framework.api.crud.BaseRequestDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
<#list model.imports as import>
import ${import};
</#list>

/**
 * ${model.name!""}
 * ${model.descr!""}
 * @author ${author!""}
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ${model.name!""} extends BaseRequestDto {

    <#if model.fields?? && (model.fields?size > 0)>
    <#list model.fields as f>
    /**
     * ${f.descr!""}
     */
    private ${f.typ!""} ${f.name!""};

    </#list>
    </#if>
}
