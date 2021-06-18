package ${package!""};

<#list documents as doc><#if doc.type == "searchDto"><#assign searchDtoSuffix="${(doc.suffix)?cap_first}" /></#if></#list>
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;
import java.time.LocalDateTime;
<#list imports as import>
import ${import};
</#list>

/**
 * @description ${comment!""}(${(name?cap_first)!""})  DTO
 *
 * @author ${author!""}
 * @date ${.now}
 * @since  1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ${(name?uncap_first)!""}${searchDtoSuffix!""} extends BaseSearchDto {
    private static final long serialVersionUID = 1L;

    <#if entity.fields?? && (entity.fields?size > 0)>
    <#list entity.fields as f>
    <#if f.descr??>/**
     * ${f.descr}
     */</#if>
    <#if f.name == "appId" || f.name == "storeId" || f.name == "rev">
    @JsonIgnore
    </#if>
    private ${(f.typ)?cap_first} ${(f.name)?uncap_first};

    </#list>
    </#if>
    /**
     * 实现父类的查询条件过滤
     *
     */
    @Override
    public void buildSearchParams(Map<String, Object> map) {
        <#if entity.fields?? && (entity.fields?size > 0)>
    <#list entity.fields as f>
        super.putNoNull("EQ_${(f.name)?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}", this.${f.name}, map);
    </#list>
    </#if>
    }

}
