package ${package!""};

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* @description ${comment!""}
*
* @author ${author!""}
* @date ${.now}
*/
@Data
<#if extendClass??>
@EqualsAndHashCode(callSuper = true)
</#if>
@NoArgsConstructor
@Accessors(chain = true)
@TableName
public class ${(name?cap_first)!""}${suffix!""} extends CrudEntity<${(name?cap_first)!""}${suffix!""}>{
<#if columns?? && (columns?size >0)>
    <#list columns as column >
    /**
     * ${column.comment!""}
     */
    private ${column.typ!""} ${column.name!""};
    </#list>
</#if>
}
