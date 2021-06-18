package ${package!""};

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* ${comment!""}(${(name?cap_first)!""}) ${suffix!""}
*
* @author ${author!""}
* @date ${.now}
* @since  1.0.0
*/
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ${(name?cap_first)!""}${suffix!""} implements Serializable  {

    private static final long serialVersionUID = -1L;

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
}

